package com.blog.authservice.service;

import com.blog.authservice.dto.request.InvalidatedTokenRequest;
import com.blog.authservice.service.base.BaseRedisServiceV2;

public interface InvalidatedTokenService extends BaseRedisServiceV2<String, String, String> {

     /**
      * @param invalidatedTokenRequest - Input InvalidatedTokenRequest Object
      */
     void createInvalidatedToken(InvalidatedTokenRequest invalidatedTokenRequest);

     /**
      * @param id - Input invalidatedTokenId
      * @return boolean indicating if the id already exited or not
      */
     boolean existById(String id);
}
