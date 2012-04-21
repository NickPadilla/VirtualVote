/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index.elasticsearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.action.bulk.BulkRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.virtualvote.index.IIndex;
import com.monstersoftwarellc.virtualvote.index.IIndexingService;
import com.monstersoftwarellc.virtualvote.query.IQueryService;

/**
 * @author nicholas
 *
 */
@Service
public class ElasticSearchIndexingService implements IIndexingService {

	private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchIndexingService.class);

	@Autowired
	private IQueryService<Client> queryService;	
	
	private List<IIndex> indices;

	/* (non-Javadoc)
	 * @see org.methodisthealth.jsonreporting.index.IIndexingService#indexData()
	 */
	@Scheduled(cron="${index.schedule}")
	public void indexData() {
	//	BulkRequestBuilder bulkRequest = queryService.getClient().prepareBulk();
		String[] indices = new String[0];// congressional years 
		int page = 0;
		// we will need to loop over our defined data structure <congressional year> - photo/ bills/ rolls/ and people.xml
		// if we already have this congressional year, index, then we search for the latest modified timestamp held within
		// each index type, what the data holds (photo,bills,rolls,people).  Once we have the lastest mod we can search those
		// directories for anything that has been modified later than that.  If so we will update the specific entries, if they
		// exist, if not we will create the new entries in the index type.
		for(String index : indices){
			// we will need to ensure we only index data that is updated or new based on modification timestamp.
			List<File> listOfXMLFiles = new ArrayList<File>();
			while(listOfXMLFiles.size() != 0){
				page++;
				for(File file : listOfXMLFiles){
					File object = null;// here we need to transform the file into the object it represents
					// if we have the people.xml we need to ensure we get a list of People objects and loop over them
					// otherwize each file is an independent object.  We will need a transform layer so that the system 
					// can elect how to transform the data based on the data.  We have both .xml and image files right now
					// but that can change so we need a clean way to id the transformer and tie that back to a domain model,
					// it should handle all errors internally. 
					/*bulkRequest.add(queryService.getClient().prepareIndex(index, row.get(indexTypeColumn), Long.toString(uniqueId))
							.setSource(StringUtils.getBytesUtf8(row.get(jsonColumn))));*/
				}
//				updateBulkInsertion(bulkRequest);
				// we need to get the next object we need to index
				// list = dataAccess.getRecords(page, number, table, uniqueId);
			}
		}
	}

	/**
	 * @param bulkRequest
	 */
	private void updateBulkInsertion(BulkRequestBuilder bulkRequest){
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			LOG.error("Bulk Input Has Errors! : " + bulkResponse.buildFailureMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.index.IIndexingService#getIndices()
	 */
	@Override
	public List<IIndex> getIndices() {
		if(indices == null){
			indices = new ArrayList<IIndex>();
		}
		return indices;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.index.IIndexingService#addIndex(com.monstersoftwarellc.virtualvote.index.IIndex)
	 */
	@Override
	public void addIndex(IIndex index) {
		if(indices == null){
			indices = new ArrayList<IIndex>();
		}
		boolean hasIndex = false;
		for(int i = 0; i < indices.size(); i++){
			IIndex temp = indices.get(i);
			if(temp.getIndexName().equals(index.getIndexName())){
				hasIndex = true;
				indices.remove(i);
				indices.add(index);
			}
		}
		if(!hasIndex){
			indices.add(index);
			createIndex(index);
		}
	}

	/**
	 * Will create a new index in ElasticSearch.  Will use the {@link IIndex#getIndexName()} for the
	 * index name. 
	 * @param index
	 */
	private void createIndex(IIndex index) {

	}
}
