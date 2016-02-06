package com.custom.core.cache;

import java.util.List;

import android.content.Context;
/***
 * 
 * 
 * 
 * @author leihen
 *
 */
public class CacheDataMaster extends AbstractCacheFileMaster {

	private Context context;

	private String fileFolderName;

	public CacheDataMaster(Context mcontext) {

		this.context = mcontext;
		this.fileFolderName = "cache";
	}

	
	
	public CacheDataMaster(Context mcontext, String fileFolderName) {

		this.context = mcontext;
		this.fileFolderName = fileFolderName;
	}

	@Override
	public void saveToCacheFile(String jsonData, String fileName) {
		if (jsonData == null) {
			return;
		}
		if (fileName == null) {
			return;
		}
		try {
			// save to /data/data
			SaveToSysFile(context, jsonData, fileFolderName, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 
	 */
	@Override
	public boolean deleteCacheFileByName(String fileName) {
		if (fileName == null) {
			return false;
		}
		try {
			return deleteCacheFileByName(context, fileName, fileFolderName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String readCacheFile(String fileName) {
		String content = null;
		if (fileName == null) {
			return null;
		}
		try {
			content = readCacheFile(context, fileName, fileFolderName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public boolean deleteAllCacheFiles() {
		try {
			return deleteAllCacheFile(context, fileFolderName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> queryAllCacheFileNameList() {
		try {
			return queryAllCacheFile(context, fileFolderName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
