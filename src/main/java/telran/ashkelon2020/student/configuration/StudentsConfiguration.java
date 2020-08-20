package telran.ashkelon2020.student.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentsConfiguration {
	
	@Bean
	public ModelMapper modelMaapper() {
		return new ModelMapper();
	}
}
