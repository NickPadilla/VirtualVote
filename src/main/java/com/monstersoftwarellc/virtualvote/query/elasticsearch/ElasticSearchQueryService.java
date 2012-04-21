/**
 * 
 */
package com.monstersoftwarellc.virtualvote.query.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.disMaxQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.action.admin.cluster.state.ClusterStateRequest;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.action.search.SearchRequestBuilder;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.FieldQueryBuilder.Operator;
import org.elasticsearch.index.query.OrFilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.virtualvote.index.IIndexingService;
import com.monstersoftwarellc.virtualvote.query.IQueryService;
import com.monstersoftwarellc.virtualvote.query.NameValue;
import com.monstersoftwarellc.virtualvote.query.QueryHit;
import com.monstersoftwarellc.virtualvote.query.QueryResponse;
import com.monstersoftwarellc.virtualvote.query.SearchOptions;

/**
 * @author nicholas
 *
 */
@Service
public class ElasticSearchQueryService implements IQueryService<Client> {

	private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchQueryService.class);
	
	@Autowired
	private IIndexingService indexingService;

	private Node elasticSearchNode;
	private Client elasticSearchClient;

	@PostConstruct
	protected void init(){
		/*this.elasticSearchNode = nodeBuilder().node().start();
		this.elasticSearchClient = elasticSearchNode.client();*/
	}

	@PreDestroy
	protected void destroy(){
		/*elasticSearchClient.close();
		elasticSearchNode.close();*/
	}

	/* (non-Javadoc)
	 * @see org.methodisthealth.jsonreporting.search.IQueryService#searchAllSpecificFields(org.methodisthealth.jsonreporting.SearchOptions)
	 */
	public QueryResponse searchAllSpecificFields(SearchOptions options) {		
		SearchRequestBuilder builder = elasticSearchClient.prepareSearch("")// all indices - congressional year
															.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)				
															.setTypes(Integer.toString(options.getSelectedFormId()))				
															.addSort("_type", SortOrder.ASC)
															.setFrom((options.getPage()-1)*options.getNumberPerPage())
															.setSize(options.getNumberPerPage());

		builder = getSearchValuesMap(options, builder);
		
		SearchResponse response = builder.execute().actionGet();
		if(LOG.isDebugEnabled()){
			LOG.debug("total time for query : " + response.getTook());
			LOG.debug("total hits in indexices : " + response.getHits().getTotalHits());
		}
		return getResults(response, false);
	}

	/* (non-Javadoc)
	 * @see org.methodisthealth.jsonreporting.search.IQueryService#searchAll(org.methodisthealth.jsonreporting.SearchOptions)
	 */
	public QueryResponse searchAll(SearchOptions options) {
		SearchRequestBuilder builder = elasticSearchClient.prepareSearch("")
															.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
															.setQuery(getSearchValues(options, Operator.OR))
															.addSort("_type", SortOrder.ASC)
															.setFrom((options.getPage()-1)*options.getNumberPerPage())
															.setSize(options.getNumberPerPage());
		if(options.getSelectedFormId() != 0){
			builder.setTypes(Integer.toString(options.getSelectedFormId()));
		}
		SearchResponse response = builder.execute().actionGet();		
		if(LOG.isDebugEnabled()){
			LOG.debug("total time for query : " + response.getTook());
			LOG.debug("total hits in indexices : " + response.getHits().getTotalHits());
		}
		return getResults(response, false);
	}

	/* (non-Javadoc)
	 * @see org.methodisthealth.jsonreporting.search.IQueryService#getExportDataFromSearchOptions(org.methodisthealth.jsonreporting.model.SearchOptions)
	 */
	public List<NameValue> getExportDataFromSearchOptions(SearchOptions searchOptions) {
		List<NameValue> results = new LinkedList<NameValue>();
		SearchRequestBuilder builder = elasticSearchClient.prepareSearch("")
		                .setSearchType(SearchType.SCAN)
		                .setScroll(new TimeValue(60000))
		                .addSort("_type", SortOrder.ASC)
		                .setSize(100);//100 hits per shard will be returned for each scroll
		
		if(searchOptions.getSelectedFormId() != 0){
			builder.setTypes(Integer.toString(searchOptions.getSelectedFormId()));
		}
		
		builder = getSearchValuesMap(searchOptions, builder);
		
		SearchResponse response = builder.execute().actionGet();	
		
		//Scroll until no hits are returned
		int totalHits = 0;
		long tookInMills = 0;
		while (true) {
			response = elasticSearchClient.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
		    QueryResponse temp = getResults(response, true);
		    totalHits += temp.getHits().size();
		    tookInMills += response.getTook().getMillis();
		    for(QueryHit hit : temp.getHits()){
		    	results.addAll(hit.getHitFields());
			}
		    if(temp.getHits().isEmpty() || temp.getHits().size() < 100){		    	
		    	// no more hits
		    	break;
		    }
		}
		if(LOG.isDebugEnabled()){
			LOG.debug("total time for export : " + TimeValue.timeValueMillis(tookInMills));
			LOG.debug("total hits for export : " + totalHits);
		}
		return results;
	} 
	
	/* (non-Javadoc)
	 * @see org.methodisthealth.jsonreporting.search.IQueryService#getIndexTypesForIndices(java.lang.String[])
	 */
	public Map<String, String> getIndexTypesForIndices(final String... indices) {
		final Map<String, String> ret = new HashMap<String, String>();
		ClusterStateRequest clusterStateRequest = Requests.clusterStateRequest().filteredIndices(indices);
		clusterStateRequest.listenerThreaded(false);
		try {
			ClusterStateResponse resp = elasticSearchClient.admin().cluster().state(clusterStateRequest).actionGet();
			MetaData metaData = resp.state().metaData();
			for (IndexMetaData indexMetaData : metaData) {
				for (MappingMetaData mappingMd : indexMetaData.mappings().values()) {
					if (!ret.isEmpty() && ret.containsKey(mappingMd.type())) {
						// filter this type out...
						continue;
					}
					ret.put(mappingMd.type(), mappingMd.source().string());				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see org.methodisthealth.jsonreporting.search.IQueryService#getFieldsForIndexType(java.lang.String)
	 */
/*	public String getFormNameForFormId(String formId) {
		String name = "";
		SearchResponse response = elasticSearchClient.prepareSearch("")
				.setSearchType(SearchType.DFS_QUERY_AND_FETCH)
				.setQuery(matchAllQuery())
				.setSize(1)
				.setExplain(true)
				.setTypes(formId)
				.addSort("_uid", SortOrder.DESC)
				.execute()
				.actionGet();
		if(LOG.isDebugEnabled()){
			LOG.debug("getFieldsForIndexType() - total time for query : " + response.getTook());
			LOG.debug("getFieldsForIndexType() - total hits in indexices : " + response.getHits().getTotalHits());
		}
		if(response.getHits().getTotalHits() > 0){
			SearchHit hit = response.getHits().getAt(0);	
			Map<String, Object> source = hit.getSource();
			int index = propertiesService.getIndexFromIndices(hit.getIndex());
			String formNameKey = propertiesService.getStringArrayProperty(
					ApplicationPropertiesEnum.INDEX_FORM_PART_FIELD)[index];
			for(Entry<String, Object> fields : source.entrySet()){
				if(fields.getValue() instanceof List){
					Object obj = ((List<?>)fields.getValue()).get(0);
					if(obj instanceof Map){
						if(((Map<?, ?>)obj).containsKey(formNameKey)){
							name = (String) ((Map<?, ?>)obj).get(formNameKey);
							break;
						} 
					}
				}
			}
		}
		return name;
	}
*/
	/* (non-Javadoc)
	 * @see org.methodisthealth.jsonreporting.index.IQueryService#getClient()
	 */
	public Client getClient() {
		return elasticSearchClient;
	}

	/**
	 * @param fields
	 * @param fieldsForForm
	 * @param forResultSet
	 */
	private void setFieldNamesMap(Map<String,Object> fields, List<NameValue> fieldsForForm, boolean forResultSet){
		boolean firstRow = true;
		for(Entry<String, Object> field : fields.entrySet()){
			// for each field we get the label			
			Object value = field.getValue();
			if(value != null){
				if(value instanceof List){
					handleList(value, fieldsForForm, forResultSet, firstRow);
				}else if(value instanceof Map){
					handleMap(value, fieldsForForm, forResultSet, firstRow);
				}
			}
		}
	}

	/**
	 * @param obj
	 * @param fieldsForForm
	 * @param forResultSet
	 */
	private void handleList(Object obj, List<NameValue> fieldsForForm, boolean forResultSet, boolean firstRow) {
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)obj;
		for(Object item : list){
			if(item instanceof List){
				handleList(item, fieldsForForm, forResultSet, firstRow);
			}else if(item instanceof Map){
				handleMap(item, fieldsForForm, forResultSet, firstRow);
				firstRow = false;
			}
		}
	}

	/**
	 * @param obj
	 * @param fieldsForForm
	 * @param forResultSet
	 */
	private void handleMap(Object obj, List<NameValue> fieldsForForm, boolean forResultSet, boolean firstRow) {
		@SuppressWarnings("unchecked")
		Map<String, Object> fields = (Map<String,Object>) obj;
		NameValue resultSetField = new NameValue();;
		boolean setDataName = false;
		boolean setDataValue = false;
		for(Entry<String, Object> field : fields.entrySet()){
			// for each field we get the label	
			String key = field.getKey();		
			Object value = field.getValue();
/*			if(value != null){
				boolean isHeader = Arrays.asList(formNamePart).contains(key);
				if(Arrays.asList(formNamePart).contains(key)){
					fieldsForForm.add(new NameValue(UUID.randomBase64UUID()+"."+key, (String) value, isHeader, firstRow));
				}else if(Arrays.asList(formDataNameFields).contains(key)){
					if(forResultSet){
						resultSetField.setDataName((String) value);
						setDataName = true;
					}else{
						fieldsForForm.add(new NameValue((String) value, "", isHeader, firstRow));
					}
				}else if(Arrays.asList(formDataValueFields).contains(key)){
					if(forResultSet){
						resultSetField.setDataValue((String) value);
						setDataValue = true;
					}
				}else if(value instanceof List){
					handleList(value, fieldsForForm, forResultSet, firstRow);
				}else if(value instanceof Map){
					handleMap(value, fieldsForForm, forResultSet, firstRow);
				}
				if(setDataValue && setDataName){
					fieldsForForm.add(resultSetField);
					resultSetField = new NameValue();
					setDataName = false;
					setDataValue = false;
				}
			}*/
		}
	}

	/**
	 * @param results
	 * @param response
	 */
	private QueryResponse getResults(SearchResponse response, boolean export) {
		List<QueryHit> results = new ArrayList<QueryHit>();
		for (SearchHit hit : response.getHits()) {
			List<NameValue> temp = new ArrayList<NameValue>();
			if(LOG.isDebugEnabled() && !export){
				LOG.debug("hit source: " + hit.sourceAsString());
			}
			Map<String, Object> source = hit.sourceAsMap();
			if(source != null){
				setFieldNamesMap(source, temp, true);
				if(!temp.isEmpty()){
					results.add(new QueryHit(temp, hit.getType()));
				}
			}
		}
		return new QueryResponse(results, response.getHits().getTotalHits());
	}

	/**
	 * Will get the schema for this given formId, will return null
	 * if there is no schema for the form.
	 * @param searchOptions
	 * @return
	 */
	private String getSchemaForForm(SearchOptions searchOptions) {
		String schema = null;
		if(searchOptions.getSelectedFormId() != 0){
			/*MappingMetaData mappingData = schemas.get(Integer.toString(searchOptions.getSelectedFormId()));
			if(mappingData != null){
				schema = mappingData.source().toString();
			}*/
		}
		return schema;
	}	
	
	/**
	 * @param search
	 * @return
	 */
	private QueryBuilder getSearchValues(SearchOptions searchOptions, Operator operator){
		DisMaxQueryBuilder builder = disMaxQuery();
		if(searchOptions.getQueryAll() != null && !searchOptions.getQueryAll().isEmpty()){
			String schema = getSchemaForForm(searchOptions);
/*			for(int index = 0; index<formDataValueFields.length; index++){
				if(schema == null || schema.contains(formDataNameFields[index])){
					BoolQueryBuilder bool = boolQuery();
					bool.should(fieldQuery(formDataValueFields[index], smartEscapeQuery(searchOptions.getQueryAll())+"*")
											.analyzeWildcard(true).defaultOperator(operator));
					builder.add(nestedQuery(formDataPaths[index], bool));
				}
			}*/
		}
		if(LOG.isDebugEnabled()){
			LOG.debug(" query : " + builder.toString());
		}
		return builder;
	}

	/**
	 * @param search
	 * @return
	 * @throws IOException 
	 */
	private SearchRequestBuilder getSearchValuesMap(SearchOptions searchOptions, SearchRequestBuilder request) {
		String schema = getSchemaForForm(searchOptions);		
		OrFilterBuilder orFilter = null;
		if(searchOptions.getFormFields() != null){
/*			for(int index = 0; index < formDataNameFields.length; index++){
				if(schema == null || schema.contains(formDataNameFields[index])){
					String formDataPath = formDataPaths[index];	
					AndFilterBuilder andFilter = new AndFilterBuilder();
					boolean hasFields = false;
					for(NameValue field : searchOptions.getFormFields()){
						if(!field.getDataValue().isEmpty() && !field.isHeader()){				
							BoolQueryBuilder temp = boolQuery();
							temp.must(fieldQuery(formDataNameFields[index], smartEscapeQuery(field.getDataName()))
									.defaultOperator(Operator.AND));
							temp.must(fieldQuery(formDataValueFields[index], smartEscapeQuery(field.getDataValue())+"*")
									.defaultOperator(Operator.AND).analyzeWildcard(true));
							andFilter.add(new NestedFilterBuilder(formDataPath,temp));
							hasFields = true;
						}
					}
					if(hasFields){
						if(orFilter == null){
							orFilter = new OrFilterBuilder();
						}
						orFilter.add(andFilter);
					}
				}
			}*/
		}
		if(searchOptions.getFormFields() == null || (searchOptions.getQueryAll() != null && !searchOptions.getQueryAll().isEmpty())){
			request.setQuery(getSearchValues(searchOptions, Operator.OR));
		}
		if(orFilter != null){
			request.setFilter(orFilter);
		}
		if(LOG.isDebugEnabled()){
			LOG.debug(request.internalBuilder().toString());
		}
		return request;
	}
	
	/** 
     * function to escape the string from bad chars for the search 
     * 
     * @param str the String that should be escaped 
     * @return an escaped String 
     */ 
    public static String smartEscapeQuery(String str) { 
    	StringBuilder sb = new StringBuilder(); 
    	for (int i = 0; i < str.length(); i++) { 
    		char c = str.charAt(i); 
    		if (c == '\\' || c == '+' || c == '-' || c == '!' || c == 
    				'(' || c == ')' || c == ':' 
    				|| c == '^' || c == '[' || c == ']' || c == '\"' 
    				|| c == '{' || c == '}' || c == '~' 
    				|| c == '?' || c == '|' || c == '&' || c == ';' 
    				|| (!Character.isSpaceChar(c) && 
    						Character.isWhitespace(c))) { 
    			sb.append('\\'); 
    		} 
    		sb.append(c); 
    	} 
    	return sb.toString(); 
    }

}