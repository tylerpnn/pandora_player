package com.tylerpnn.json.response;

public class ExplainTrackResponse extends JsonResponse {
	
	public static class Result {
		
		public static class Explanation {
			
			private String focusTraitName, focusTraitId;

			public String getFocusTraitName() {
				return focusTraitName;
			}

			public void setFocusTraitName(String focusTraitName) {
				this.focusTraitName = focusTraitName;
			}

			public String getFocusTraitId() {
				return focusTraitId;
			}

			public void setFocusTraitId(String focusTraitId) {
				this.focusTraitId = focusTraitId;
			}
		}
		
		private Explanation[] explanations;

		public Explanation[] getExplanations() {
			return explanations;
		}

		public void setExplanations(Explanation[] explanations) {
			this.explanations = explanations;
		}
	}
	
	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}
