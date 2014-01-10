package egovframework.eam.api.system;

import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

public class SystemServiceUtil {
	public static final String DIGEST_ALGORITHM = "sha-256";
	
	public static String getAuthKey(String algorithm, String password) {
		EgovPasswordEncoder encoder = new EgovPasswordEncoder();
		
		encoder.setAlgorithm(algorithm);
		
		return encoder.encryptPassword(password);
	}
	
	public static void main(String[] args) {
		if (args.length < 2) {
		    System.out.println("Arguments missing!!!");
		    System.out.println();
		    System.out.println("Usage: java ... " + SystemServiceUtil.class.getName() + " 'algorithm' 'password'");
		    System.out.println("\t- algorithm : Message Digest Algorithms (ex: MD5, SHA-1, SHA-256, ...)");
		    System.out.println();
		    System.out.println("Ex: java ... " + SystemServiceUtil.class.getName() + " SHA-256 sample123");
		    
		    return;
		}
		
		System.out.println("Digested Password : " + getAuthKey(args[0], args[1]));
	}
}
