<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>图片上传</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!-- The Templates plugin is included to render the upload/download listings -->
<script src="js/jquery/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->

<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="js/jquery/canvas-to-blob.min.js"></script>
<script src="js/jquery/angular.min.js"></script>
<script src="js/jquery/jquery-1.10.1.min.js"></script>
<script src="js/jquery/jquery.ui.widget.js"></script>
<script src="js/jquery/jquery.iframe-transport.js"></script>
<script src="js/jquery/jquery.fileupload.js"></script>
<script src="js/jquery/jquery.fileupload-process.js"></script>
<script src="js/jquery/jquery.fileupload-angular.js"></script>
<script src="js/jquery/jquery.fileupload-ui.js"></script>
<script src="js/jquery/jquery.fileupload-validate.js"></script>
<script src="js/jquery/load-image.min.js"></script>
<script src="js/jquery/jquery.fileupload-resize.js"></script>

<!--<link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-responsive.min.css">-->

<script src="js/bootstrap/bootstrap.min.js"></script>

<script src="js/jquery/bootstrap-image-gallery.min.js"></script>

<script src="js/imageUpload.js"></script>

<link href="css/bootstrap/bootstrap.css" type="text/css"
	rel="stylesheet" />
<!-- Generic page styles -->


<!-- Bootstrap CSS fixes for IE6 -->
<!--[if lt IE 7]><link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-ie6.min.css"><![endif]-->
<!-- Bootstrap Image Gallery styles -->
<link rel="stylesheet" href="css/bootstrap-image-gallery.min.css">

<link href="css/dropzone.css" type="text/css" rel="stylesheet" />
<link href="css/jquery/jquery.fileupload-ui.css" type="text/css"
	rel="stylesheet" />
	<style type="text/css">
	.preview img{max-width:62,height:64}</style>

<!-- CSS adjustments for browsers with JavaScript disabled -->
<noscript>
	<link rel="stylesheet"
		href="css/jquery/jquery.fileupload-ui-noscript.css">
</noscript>
</head>

<body>
	<h1>图片上传</h1>
	<div style="width:600px;padding:20px">

		<form id="fileupload" action="upload/images" method="POST"
			enctype="multipart/form-data">
			<!-- Redirect browsers with JavaScript disabled to the origin page -->
			<noscript>
				<input type="hidden" name="redirect"
					value="http://blueimp.github.com/jQuery-File-Upload/">
			</noscript>
			<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
			<div class="row fileupload-buttonbar">
				<div class="span7">
					<!-- The fileinput-button span is used to style the file input field as button -->
					<span class="btn btn-success fileinput-button"> <i
						class="icon-plus icon-white"></i> <span>Add files...</span> <input
						type="file" name="files[]" multiple>
					</span>
					<button type="submit" class="btn btn-primary start">
						<i class="icon-upload icon-white"></i> <span>Start upload</span>
					</button>
					<button type="reset" class="btn btn-warning cancel">
						<i class="icon-ban-circle icon-white"></i> <span>Cancel
							upload</span>
					</button>
					<button type="button" class="btn btn-danger delete">
						<i class="icon-trash icon-white"></i> <span>Delete</span>
					</button>
					<input type="checkbox" class="toggle">
					<!-- The loading indicator is shown during file processing -->
					<span class="fileupload-loading"></span>
				</div>
				<!-- The global progress information -->
				<div class="span5 fileupload-progress fade">
					<!-- The global progress bar -->
					<div class="progress progress-success progress-striped active"
						role="progressbar" aria-valuemin="0" aria-valuemax="100">
						<div class="bar" style="width:0%;"></div>
					</div>
					<!-- The extended global progress information -->
					<div class="progress-extended">&nbsp;</div>
				</div>
			</div>
			<!-- The table listing the files available for upload/download -->
			<table role="presentation" class="table table-striped">
				<tbody class="files" data-toggle="modal-gallery"
					data-target="#modal-gallery"></tbody>
			</table>
		</form>
		<!-- modal-gallery is the modal dialog used for the image gallery -->
		<div id="modal-gallery" class="modal modal-gallery hide fade"
			data-filter=":odd" tabindex="-1">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">&times;</a>
				<h3 class="modal-title"></h3>
			</div>
			<div class="modal-body">
				<div class="modal-image"></div>
			</div>
			<div class="modal-footer">
				<a class="btn modal-download" target="_blank"> <i
					class="icon-download"></i> <span>Download</span>
				</a> <a class="btn btn-success modal-play modal-slideshow"
					data-slideshow="5000"> <i class="icon-play icon-white"></i> <span>Slideshow</span>
				</a> <a class="btn btn-info modal-prev"> <i
					class="icon-arrow-left icon-white"></i> <span>Previous</span>
				</a> <a class="btn btn-primary modal-next"> <span>Next</span> <i
					class="icon-arrow-right icon-white"></i>
				</a>
			</div>

		</div>

		<!-- The template to display files available for upload -->
		<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            {% if (file.error) { %}
                <div><span class="label label-important">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <p class="size">{%=o.formatFileSize(file.size)%}</p>
            {% if (!o.files.error) { %}
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
            {% } %}
        </td>
        <td>
            {% if (!o.files.error && !i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start">
                    <i class="icon-upload icon-white"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="icon-ban-circle icon-white"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
		<!-- The template to display files available for download -->
		<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.urlThumb) { %}
                    <a href="{%=file.urlThumb%}" title="{%=file.imageName%}" data-gallery="gallery" download="{%=file.imageName%}"><img src="{%=file.urlThumb%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                <a href="{%=file.url%}" title="{%=file.imageName%}" data-gallery="gallery" download="{%=file.imageName%}">{%=file.imageName%}</a>
            </p>
            {% if (file.error) { %}
                <div><span class="label label-important">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.imageSize)%}</span>
        </td>
        <td>
            <button class="btn btn-danger delete" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                <i class="icon-trash icon-white"></i>
                <span>Delete</span>
            </button>
            <input type="checkbox" name="delete" value="1" class="toggle">
        </td>
    </tr>
{% } %}
</script>
	</div>
</body>
</html>
