package com.ssafy.cheertogether.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseService {

	@Value("${app.firebase-bucket}")
	private String firebaseBucket;

	public String uploadFiles(MultipartFile file) {
		String fileName = makeName();
		Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
		InputStream content = null;
		try {
			content = new ByteArrayInputStream(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Blob blob = bucket.create(fileName, content, file.getContentType());
		return blob.getMediaLink();
	}

	private String makeName() {
		return UUID.randomUUID() + "_" + LocalDateTime.now();
	}
}
