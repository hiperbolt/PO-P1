package prr.core.exception;

/** Thrown when an application is not associated with a file. */
public class MissingFileAssociationException extends Exception {
  private static final long serialVersionUID = 202208091753L;
  String _filename;
  public MissingFileAssociationException(String filename) {
    super("Erro a processar ficheiro " + filename);
    _filename = filename;
  }
}
