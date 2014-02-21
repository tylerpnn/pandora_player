package pandora;

public class ErrorHandler {

	public static class PandoraServerException extends Exception {
		public PandoraServerException(String msg) {
			super(msg);
		}
	}
	
	public static void errorCheck(int errorCode) throws PandoraServerException {
		throw new PandoraServerException("Status: fail, Code: " + errorCode);
	}
}
