package com.autopartner.exception;

public class EqualsIdException extends RuntimeException{

  public EqualsIdException(Long parentId, Long id) {
    super("ParentId: " + parentId + " equals current id: " + id);
  }

}
