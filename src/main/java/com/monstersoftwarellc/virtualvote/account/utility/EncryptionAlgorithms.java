/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account.utility;

import java.security.MessageDigest;

/**
 * Common {@link MessageDigest} Algorithms. 
 * @author nicholas
 * @since 1.0
 */
public enum EncryptionAlgorithms {
	/**
	 * The MD2 message digest algorithm as defined in <a href="http://www.ietf.org/rfc/rfc1319.txt">RFC 1319</a>.
	 */
	MD2("MD2"),
	/**
	 * The MD5 message digest algorithm as defined in <a href="http://www.ietf.org/rfc/rfc1321.txt">RFC 1321</a>.
	 */
	MD5("MD5"),
	/**
	 * Hash algorithms defined in the <a href="http://csrc.nist.gov/publications/fips/index.html">FIPS PUB 180-2</a>.
	 * <br/><br/>
	 * SHA-256 is a 256-bit hash function intended to provide 128 bits of security against collision attacks, 
	 * while SHA-512 is a 512-bit hash function intended to provide 256 bits of security. 
	 * A 384-bit hash may be obtained by truncating the SHA-512 output.
	 */
	SHA1("SHA-1"),
	/**
	 * Hash algorithms defined in the <a href="http://csrc.nist.gov/publications/fips/index.html">FIPS PUB 180-2</a>.
	 * <br/><br/>
	 * SHA-256 is a 256-bit hash function intended to provide 128 bits of security against collision attacks, 
	 * while SHA-512 is a 512-bit hash function intended to provide 256 bits of security. 
	 * A 384-bit hash may be obtained by truncating the SHA-512 output.
	 */
	SHA256("SHA-256"),
	/**
	 * Hash algorithms defined in the <a href="http://csrc.nist.gov/publications/fips/index.html">FIPS PUB 180-2</a>.
	 * <br/><br/>
	 * SHA-256 is a 256-bit hash function intended to provide 128 bits of security against collision attacks, 
	 * while SHA-512 is a 512-bit hash function intended to provide 256 bits of security. 
	 * A 384-bit hash may be obtained by truncating the SHA-512 output.
	 */
	SHA384("SHA-384"),
	/**
	 * Hash algorithms defined in the <a href="http://csrc.nist.gov/publications/fips/index.html">FIPS PUB 180-2</a>.
	 * <br/><br/>
	 * SHA-256 is a 256-bit hash function intended to provide 128 bits of security against collision attacks, 
	 * while SHA-512 is a 512-bit hash function intended to provide 256 bits of security. 
	 * A 384-bit hash may be obtained by truncating the SHA-512 output.
	 */
	SHA512("SHA-512");
	private String name;
	EncryptionAlgorithms(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
}