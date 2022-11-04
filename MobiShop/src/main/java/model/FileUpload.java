package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import dao.FileUploadDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class FileUpload {
	private int id;
	private int modelId;
	private String modelName;
	private String path;
	private String name;
	private String extension;
	private Date createdAt;
	private Date updatedAt;
	
	public FileUpload() {
		
	}

	public FileUpload(int id, int modelId, String modelName, String path, String name, String extension, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.modelId = modelId;
		this.modelName = modelName;
		this.path = path;
		this.name = name;
		this.extension = extension;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public static FileUpload read(int modelId, String modelName) {
		return FileUploadDAO.read(modelId, modelName);
	}
	
	public static int create(ServletContext context, Part filePart, String path, int modelId, String modelName) {
		String fileName = getFileName(modelId, filePart);
		int result = FileUploadDAO.create(new FileUpload(-1, modelId, modelName, path, fileName, getExtension(fileName), null, null));
		
		if (result > 0) {
			upload(filePart, context.getRealPath("") + path, modelId);
			
			// Upload for local
			String localPath = context.getInitParameter("LocalPath");
			File file = new File(localPath);
			if (file.exists()) {
				upload(filePart, localPath + path, modelId);
			}
		}
		
		return result;
	}
	
	public static int update(ServletContext context, Part filePart, String path, int modelId, String modelName) {
		FileUpload itemBeforeUpdate = read(modelId, modelName);
		String fileName = getFileName(modelId, filePart);
		String localPath = context.getInitParameter("LocalPath");
		boolean isLocal = new File(localPath).exists();
		
		int result = FileUploadDAO.update(new FileUpload(0, modelId, modelName, path,fileName, getExtension(fileName), null, null));
		
		if (result > 0) {
			if (itemBeforeUpdate != null) {
				deleteFile(context.getRealPath("") + itemBeforeUpdate.getPath() + File.separator + itemBeforeUpdate.getName());
				
				// Upload for local
				if (isLocal) {
					deleteFile(localPath + itemBeforeUpdate.getPath() + File.separator + itemBeforeUpdate.getName());
				}
			}
			upload(filePart, context.getRealPath("") + path, modelId);
			
			// Upload for local
			if (isLocal) {
				upload(filePart, localPath + path, modelId);
			}
		}
		
		return result;
	}
	
	public static int delete(int modelId, String modelName) {
		FileUpload itemBeforeUpdate = read(modelId, modelName);
		int result = FileUploadDAO.delete(modelId, modelName);
		
		if (result > 0) {
			if (itemBeforeUpdate != null) {
				deleteFile(itemBeforeUpdate.getPath() + File.separator + itemBeforeUpdate.getName());
			}
			
			return itemBeforeUpdate.getId();
		}
		return -1;
	}
	
	public static void upload(Part filePart, String path, int modelId) {

	    // Create path components to save the file
	    final String fileName = getFileName(modelId, filePart);

	    OutputStream out = null;
	    InputStream filecontent = null;

	    try {
	        out = new FileOutputStream(new File(path + File.separator
	                + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        System.out.println("New file " + fileName + " created at " + path);
	    } catch (FileNotFoundException fne) {
	    	System.out.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	    	System.out.println("ERROR: " + fne.getMessage());
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
		            out.close();
		        }
		        if (filecontent != null) {
		            filecontent.close();
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	    }
	}
	
	private static void deleteFile(String path) {
		try {
			File f= new File(path);
			
			if(f.delete()) {  
				System.out.println(f.getName() + " deleted");
			} else {  
				System.out.println("failed");  
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	private static String getFileName(int modelId, final Part part) {
		return "thumbnail_" + modelId + "." + getExtension(getFileName(part));
	}
	
	private static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
