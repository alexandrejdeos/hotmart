package br.com.hotmart.response;


public class FileDetails {

	private String fileName;
	private byte byteFile[];
	private String username;
	private Long finishTime;
	private Integer qtdChunks;
	
	public String getFileName() {
		return fileName;
	}
	public byte[] getByteFile() {
		return byteFile;
	}
	public String getUsername() {
		return username;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setByteFile(byte[] file) {
		this.byteFile = file;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getQtdChunks() {
		return qtdChunks;
	}
	public void setQtdChunks(Integer qtdChunks) {
		this.qtdChunks = qtdChunks;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	
}
