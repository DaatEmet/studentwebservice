package telran.ashkelon2020.student.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.student.dao.StudentRepository;
import telran.ashkelon2020.student.dto.ScoreDto;
import telran.ashkelon2020.student.dto.StudentDto;
import telran.ashkelon2020.student.dto.StudentResponseDto;
import telran.ashkelon2020.student.dto.StudentUpdateDto;
import telran.ashkelon2020.student.dto.exceptions.StudentNotFoundException;
import telran.ashkelon2020.student.model.Student;

@Component
public class SudentServiceImpl implements StudentService {
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public boolean addStudent(StudentDto studentDto) {
		Student student = new Student(studentDto.getId(), studentDto.getName(), studentDto.getPassword());
		return studentRepository.addStudent(student);
	}

	@Override
	public StudentResponseDto findStudent(int id) {
		Student student = studentRepository.findStudentById(id);
		if(student == null) return null;
		else {
			return StudentResponseDto
					.builder()
					.id(id)
					.name(student.getName())
					.scores(student.getScores())
					.build();
		}
	}

	@Override
	public StudentResponseDto deleteStudent(int id) {
		Student student = studentRepository.deleteStudent(id);
		if(student == null) return null;
		return StudentResponseDto
				.builder()
				.id(id)
				.name(student.getName())
				.scores(student.getScores())
				.build();
	}

	@Override
	public StudentDto updateStudent(int id, StudentUpdateDto studentUpdateDto) {
		Student student = studentRepository.findStudentById(id);
		if(student == null) return null;
		else {
			student.setName(studentUpdateDto.getName());
			student.setPassword(studentUpdateDto.getPassword());
			return StudentDto
					.builder()
					.id(id)
					.name(student.getName())
					.password(student.getPassword())
					.build();
		}
	}

	@Override
	public boolean addScore(int id, ScoreDto scoreDto) {
		try {
			Student student = studentRepository.findStudentById(id);
			if(student == null) return false;
			return student.getScores().put(scoreDto.getExamName(), scoreDto.getScore()) == null;
		} catch (NullPointerException e) {
			throw new StudentNotFoundException(id);
		}
	}

}
