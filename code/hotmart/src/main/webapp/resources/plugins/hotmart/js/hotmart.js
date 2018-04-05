$(document).ready(function(){
    $.get("/login/getLoginUser", function(data, status){
        $("#nameUsuarioLogado").text('Usuário: ' + data.name);
    });
});

$(function () {
	var qntBlocos;
    $('#fileupload').fileupload({
    	maxChunkSize: 1000000*1,
        dataType: 'json',
        done: function (e, data) {
        	if (data._progress.total < 1000000*1) {
    			criarLinha(data.result[data.result.length-1], false, data.result.length-1, 1, 'Concluído');			
    		}
        },
    	error: function(e,data){
    		alert("Falha ao enviar o arquivo!");
    	}
    }).on('fileuploadchunksend', function (e, data) {
    	
	}).on('fileuploadchunkdone', function (e, data) {
		if (data._progress.loaded == 1000000 || data._progress.total < 1000000) {
			qntBlocos = Math.ceil(data._progress.total/data._progress.loaded);
			status = "Em andamento";
			criarLinha(data.result[data.result.length-1], false, data.result.length-1, qntBlocos, status);
		}else if (data._progress.loaded == data._progress.total) {
			status = "Concluído";
			criarLinha(data.result[data.result.length-1], true, data.result.length-1, qntBlocos, status);
		}
	}).on('fileuploadchunkfail', function (e, data) {
		status = "Falha";
		criarLinha(data.result[data.result.length-1], false, data.result.length-1, qntBlocos, status);
	});
});

function criarLinha(arquivo, remover, index, qntBlocos, status) {
	if (remover) {
		$("tbody > tr").last().remove();		
	}
	
	var newRow = $("<tr id="+ index +" >");
    var cols = "";

    cols += '<td>'+ arquivo.username +'</td>';
    cols += '<td>'+ arquivo.fileName +'</td>';
    cols += '<td>'+ status +'</td>';
    cols += '<td>'+ arquivo.finishTime +'</td>';
    cols += '<td>'+ qntBlocos +'</td>';
    if(status == "Concluído"){
    	cols += '<td><a href="/file/get/'+index+'">Download</a>'+'</td>';
    }
    newRow.append(cols);
    $("#uploaded-files").append(newRow);	
}



