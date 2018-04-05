package br.com.hotmart.rest.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import br.com.hotmart.response.FileDetails;

@Api(value = "file") 
@RestController
@RequestMapping(value = { "/file" })
public class FileRestController extends AbstractBaseFile{
	private LinkedList<FileDetails> listFileDetails = new LinkedList<>();

	/**
	 * Serviço resonsável por fazer upload do arquivo por chunk ou não.
	 * @param request
	 * @param response
	 * @return LinkedList<FileDetails>
	 */
	@ApiOperation(value = "Upload arquivo em chunck" )
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/uploadFile")
	public @ResponseBody ResponseEntity<LinkedList<FileDetails>> uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {
		Long start = System.currentTimeMillis();
		MultipartFile multipartFile = null;
		FileDetails fileDetails = null;
		try {
			Iterator<String> iterator = request.getFileNames();
			while (iterator.hasNext()) {
				multipartFile = request.getFile(iterator.next());
				String fileName = multipartFile.getOriginalFilename();

				if(!this.listFileDetails.isEmpty()) {
					List<FileDetails> listFDetails = this.listFileDetails.stream().filter(object -> object.getFileName().equals(fileName)).collect(Collectors.toList());
					
					if(listFDetails == null || listFDetails.isEmpty()) {
						Long finish = System.currentTimeMillis();
						this.listFileDetails.add(super.createFileDetails(multipartFile.getBytes(), fileName, 1, (finish - start)));
						return ResponseEntity.status(HttpStatus.OK).body(this.listFileDetails);
					}
					
					fileDetails = listFDetails.get(0);
					fileDetails.setByteFile(ArrayUtils.addAll(fileDetails.getByteFile(),multipartFile.getBytes()));
					fileDetails.setQtdChunks(fileDetails.getQtdChunks() + 1);
					Long finish = System.currentTimeMillis();
					fileDetails.setFinishTime((finish - start) + fileDetails.getFinishTime());
					return ResponseEntity.status(HttpStatus.OK).body(this.listFileDetails);
				}else {
					Long finish = System.currentTimeMillis();
					listFileDetails.add(super.createFileDetails(multipartFile.getBytes(), fileName, 1, (finish - start)));
					return ResponseEntity.status(HttpStatus.OK).body(this.listFileDetails);
				}
			}
			
		} catch (Exception e) {
			super.log.error(e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this.listFileDetails);
	}
	
	/**
	 * 
	 * @param response
	 * @param value
	 */
	@ApiOperation(value = "download arquivo enviado" )
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/get/{value}")
	 public void get(HttpServletResponse response, @PathVariable String value){
		try {		
				FileDetails getFile = listFileDetails.get(Integer.parseInt(value));
			 	response.setContentType(getFile.getFileName());
			 	response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
		        FileCopyUtils.copy(getFile.getByteFile(), response.getOutputStream());
		 }catch (IOException e) {
				super.log.error(e);
		 }
	 }
}
