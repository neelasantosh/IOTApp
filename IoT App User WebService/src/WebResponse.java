import java.io.Serializable;

public class WebResponse implements Serializable {
	
	private String result;
	private Object data;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public WebResponse(String result, Object data) {
		super();
		this.result = result;
		this.data = data;
	}
	public WebResponse() {}
	
}
