package com.tylerpnn.json.response;

public class ExplainTrackResponse extends JsonResponse {
	
	public static class Result {
		
		public static class Explanation {
			
			private String focusTraitName, focusTraitId;

			public String getFocusTraitName() {
				return focusTraitName;
			}
			public String getFocusTraitId() {
				return focusTraitId;
			}
		}
		
		private Explanation[] explanations;

		public Explanation[] getExplanations() {
			return explanations;
		}
	}
	
	private Result result;

	public Result getResult() {
		return result;
	}
}
