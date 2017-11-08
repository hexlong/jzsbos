package cn.itcast.cxfserver;

import javax.jws.WebService;

@WebService
public interface WeatherService {

	public String getInfoByCityName(String string);
}
