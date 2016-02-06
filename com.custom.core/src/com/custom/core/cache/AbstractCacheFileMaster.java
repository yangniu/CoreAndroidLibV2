package com.custom.core.cache;

import java.io.File;
import java.util.List;

import com.custom.core.util.io.FileUtils;



import android.content.Context;

public abstract class AbstractCacheFileMaster {
	
	
	private long validTime =0l;
	
	protected abstract void saveToCacheFile(String jsonData,String fileName);
	
	protected abstract String readCacheFile(String fileName);
	
	protected abstract boolean deleteCacheFileByName(String fileName);
	
	protected abstract boolean deleteAllCacheFiles();
	
	protected abstract List<String> queryAllCacheFileNameList();
	
	
	
	/**
	 * /data/data/...
	 * @param context
	 * @param jsonData
	 * @param fileFolderName
	 * @param fileName
	 * @throws Exception
	 */
	protected void SaveToSysFile(Context context,String jsonData,String fileFolderName,String fileName) throws Exception{
		
		if(jsonData==null){
			return;
		}
		String fileFolderPath=getSysSaveFilePath(context, fileFolderName);
		if(!FileUtils.isAbsolutePackageExist(fileFolderPath)){
			FileUtils.createAbsoluteDir(fileFolderPath);
		}
		String cacheFilePath=fileFolderPath+File.separator+fileName;
		saveDataToFile(cacheFilePath, jsonData);
		
	}

	
	private void saveDataToFile(String cacheFilePath,String jsonData) throws Exception{
		
		//create log file
		File cacheFile=new File(cacheFilePath);		
		if(!cacheFile.exists()){
			cacheFile.createNewFile();
		}
		FileUtils.write(cacheFile, jsonData);
	}
	/***
	 * @param context
	 * @param fileName
	 * @param fileFolderName
	 * @param isSdCard
	 * @return
	 * @throws Exception 
	 */
	protected String readCacheFile(Context context,String fileName,String fileFolderName) throws Exception {
		if(fileName==null){
			return null;
		}
		String	fileFolderPath=getSysSaveFilePath(context, fileFolderName);
		if(new File(fileFolderPath).exists()){
			String cacheFilePath=fileFolderPath+File.separator+fileName;
			File cacheFile=new File(cacheFilePath);
			if(cacheFile.exists()&&isCacheValid(cacheFile,getValidTime())){
				return FileUtils.read(cacheFile);
			}
		}
		return null;
	}
	
	public long getValidTime() {
		return validTime;
	}

	public void setValidTime(long validTime) {
		this.validTime = validTime;
	}

	/***
	 * @param context
	 * @param fileName
	 * @param fileFolderName
	 * @param isSdCard
	 * @return
	 */
	protected boolean deleteCacheFileByName(Context context,String fileName,String fileFolderName) {
		if(fileName==null){
			return false;
		}
		String	fileFolderPath=getSysSaveFilePath(context, fileFolderName);
		if(new File(fileFolderPath).exists()){
			String cacheFilePath=fileFolderPath+File.separator+fileName;
			File CacheFile=new File(cacheFilePath);
			if(CacheFile.exists()){
				return FileUtils.deleteFile(cacheFilePath);
			}
		}
		return false;
	}
	/***
	 * @param context
	 * @param isSdCard 
	 * @param fileFolderName
	 * @return
	 */
	public boolean deleteAllCacheFile(Context context,String fileFolderName){
		
		String	fileFolderPath=getSysSaveFilePath(context, fileFolderName);
		if(new File(fileFolderPath).exists()){
			FileUtils.deleteDirectory(fileFolderPath);
		}
		return true;
	}
	/***
	 * @param context
	 * @param fileFolderName
	 * @param isSdCard
	 * @return
	 */
	public List<String> queryAllCacheFile(Context context,String fileFolderName){
		
		String	fileFolderPath=getSysSaveFilePath(context, fileFolderName);
		if(new File(fileFolderPath).exists()){
			return 	FileUtils.queryFileNameList(fileFolderPath);
		}
		return null;
	}
	
	/**
	 *
	 * @return
	 */
	protected String getSysSaveFilePath(Context context,String fileFolderName){
		
		return context.getCacheDir().getPath()+File.separator+fileFolderName;
	}
	
	
	public boolean isCacheValid(File file ,long validTime) {
		
		if (validTime > 0) {
			long durationTime = System.currentTimeMillis()- file.lastModified();
			durationTime /= 1000;
			if (durationTime < validTime) {
				return true;
			}
		}
		return false;
	}
}
