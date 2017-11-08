package cn.itcast.cxfserver;

public class WeatherServiceImpl implements WeatherService {

	@Override
	public String getInfoByCityName(String string) {
		
		if(string.equals("北京")){
			return "多云";
		}else{
			return "大晴天";
		}
	}

}
