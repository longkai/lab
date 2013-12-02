/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-22
 * @Mail im.longkai@gmail.com
 */
package cn.newgxu.lab.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * 文件上传辅助类（辅助处理大小，类型，上传路径等），直接调用即可。
 *
 * @User longkai
 * @Date 13-8-16
 * @Mail im.longkai@gmail.com
 */
public class FileTransfer {

	private static Logger l = LoggerFactory.getLogger(FileTransfer.class);

	private FileTransfer() {
	}

	/**
	 * 将给定的字节数组存储为本地的一个文件
	 *
	 * @param bytes          需要存储为文件的字节数组
	 * @param fileSystemPath 存储路径（文件系统的路径）
	 * @param relativePath   相对于存储路径的路径（可以为空，如果用于web url，注意和web url的根路径对应）
	 * @param maxSize        最大长度
	 * @param supportedExts  支持的文件后缀名（从fileName参数中获得），为空表示全部支持
	 * @param fileName       文件名（不能为空！）
	 * @param rename         是否需要系统重命名，不需要就按照给定的文件名（但是可能会覆盖同名文件），否则按照当前时间毫秒值+后缀名
	 * @return 存储文件的相对路径（relativePath + fileName)
	 */
	public static final String fileTransfer(final byte[] bytes, final String fileSystemPath, String relativePath,
	                                        long maxSize, String[] supportedExts, String fileName, boolean rename) {
		checkSize(bytes, maxSize);
		String ext = resolveExt(fileName, supportedExts);

		// resolve file name
		if (rename) {
			fileName = System.currentTimeMillis() + "." + ext;
		}

		File dir = mkdir(fileSystemPath, relativePath);

		Resources.openOutputStream(dir.getAbsolutePath() + "/" + fileName, false, new Resources.ResourcesCallback() {
			@Override
			protected void onSuccess(OutputStream os) throws IOException {
				os.write(bytes);
			}

			@Override
			protected void onError(Throwable t) {
				throw new RuntimeException("ERROR when writing file!", t);
			}
		});
		l.info("upload file to: {} ok!", dir.getPath());
		return relativePath == null ? fileName : relativePath + fileName;
	}

	private static void checkSize(final byte[] bytes, long maxSize) {
		// check file' s size
		if (bytes.length == 0) {
			throw new IllegalArgumentException("the file is empty! fail to transfer!");
		}
		if (bytes.length > maxSize) {
			throw new IllegalArgumentException("the file' s size overflow! the MAX size is " + (maxSize / 1024) + " kb!");
		}
	}

	private static String resolveExt(String fileName, String[] supportedExts) {
		// check file' s type
		final String ext = TextUtils.getFileExt(fileName);
		if (supportedExts != null) {
			boolean support;
			for (int i = 0; !(support = supportedExts[i].equalsIgnoreCase(ext)); i++)
				;
			if (!support) {
				throw new IllegalArgumentException("not supported file type! the supported type is " + Arrays.toString(supportedExts));
			}
		}
		return ext;
	}

	private static File mkdir(String fileSystemPath, String relativePath) {
		if (relativePath == null) {
			relativePath = "";
		}
		// resolve file directory
		File dir = new File(fileSystemPath + "/" + relativePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

}
