package org.cai.exception

/**
 * @author cai584770
 * @date 2024/5/20 12:47
 * @Version
 */
class BioSequenceTypeException extends Exception{
  override def getMessage: String = "Invalid BioSequence type provided."
}
