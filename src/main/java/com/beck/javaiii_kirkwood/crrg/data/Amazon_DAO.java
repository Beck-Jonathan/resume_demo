package com.beck.javaiii_kirkwood.crrg.data;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

public class Amazon_DAO {

  public static void main(String[] args) throws IOException {
    Regions clientRegion = Regions.US_EAST_1;
    String bucketName = "derbypictures";

    String fileObjKeyName = "album_accessor_test";
    String fileName = "c:\\Table_Gen\\CSharp\\Album_Accessor.txt";
    try {
      // This code expects that you have AWS credentials set up per:
      // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
      AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
          .withRegion(clientRegion)
          .build();

      // Upload a text string as a new object.


      // Upload a file as a new object with ContentType and title specified.
      PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType("plain/text");
      metadata.addUserMetadata("title", "someTitle");
      request.setMetadata(metadata);
      s3Client.putObject(request);
    } catch (AmazonServiceException e) {
      // The call was transmitted successfully, but Amazon S3 couldn't process
      // it, so it returned an error response.
      e.printStackTrace();
    } catch (SdkClientException e) {
      // Amazon S3 couldn't be contacted for a response, or the client
      // couldn't parse the response from Amazon S3.
      e.printStackTrace();
    }
  }
}



