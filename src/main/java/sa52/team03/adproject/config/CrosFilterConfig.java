package sa52.team03.adproject.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sa52.team03.adproject.filter.CrosFilter;

@Configuration
public class CrosFilterConfig {
	@Bean
	public FilterRegistrationBean<CrosFilter> filterRegistrationBean() {
		FilterRegistrationBean<CrosFilter> registrationBean = new FilterRegistrationBean<>();
		CrosFilter crosFilter = new CrosFilter();
		registrationBean.setFilter(crosFilter);
		return registrationBean;
	}
}
