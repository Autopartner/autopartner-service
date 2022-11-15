package com.autopartner.exception;

public class ParentIdException extends RuntimeException{

  public ParentIdException(Long parentId, Long id) {
    super("ParentId: " + parentId + " equals current id: " + id);
  }

}
