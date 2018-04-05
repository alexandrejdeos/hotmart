package br.com.hotmart.rest.controller;

import br.com.hotmart.response.FileDetails;

public abstract class AbstractBaseFile extends AbstractBaseUser {

	/**
	 * 
	 * @param file
	 * @param fileName
	 * @param qtdBlocos
	 * @param sentTime
	 * @return
	 */
	protected FileDetails createFileDetails(byte file[], String fileName, Integer qtdChunks, Long finishTime) {
		FileDetails fileDetails = new FileDetails();
		fileDetails.setByteFile(file);
		fileDetails.setFileName(fileName);
		fileDetails.setQtdChunks(qtdChunks);
		fileDetails.setFinishTime(finishTime);
		fileDetails.setUsername(super.getUsername());
		return fileDetails;
	}


}
