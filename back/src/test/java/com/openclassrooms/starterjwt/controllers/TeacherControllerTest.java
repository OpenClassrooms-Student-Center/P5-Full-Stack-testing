package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherControllerTest {
  @Mock
  private TeacherService teacherService;

  @Mock
  private TeacherMapper teacherMapper;

  @InjectMocks
  private TeacherController teacherController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindByIdValidIdReturnsTeacher() {
    Teacher teacher = new Teacher();
    TeacherDto teacherDto = new TeacherDto();
    when(teacherService.findById(1L)).thenReturn(teacher);
    when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

    ResponseEntity<?> response = teacherController.findById("1");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(teacherDto, response.getBody());
  }

  @Test
  void testFindByIdInvalidIdReturnsBadRequest() {
    ResponseEntity<?> response = teacherController.findById("invalid");

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void testFindByIdNonExistentIdReturnsNotFound() {
    when(teacherService.findById(1L)).thenReturn(null);

    ResponseEntity<?> response = teacherController.findById("1");

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testFindAllReturnsAllTeachers() {
    List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher());
    List<TeacherDto> teacherDtos = Arrays.asList(new TeacherDto(), new TeacherDto());
    when(teacherService.findAll()).thenReturn(teachers);
    when(teacherMapper.toDto(teachers)).thenReturn(teacherDtos);

    ResponseEntity<?> response = teacherController.findAll();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(teacherDtos, response.getBody());
  }
}
