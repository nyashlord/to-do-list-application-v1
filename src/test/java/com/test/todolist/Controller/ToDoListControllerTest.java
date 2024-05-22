package com.test.todolist.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.todolist.entity.Task;
import com.test.todolist.service.ToDoListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = ToDoListController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ToDoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoListService toDoListService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;


    @Test
    public void ToDoListController_GetAllTasks_ReturnAllItems() throws Exception {
       Task task = new Task()
                .setDescription("Eat")
                .setComplete(true);
       Task task2 = new Task()
               .setDescription("Swim")
               .setComplete(true);
        List<Task> tasks = Arrays.asList(task, task2);

        when(toDoListService.getAllItems()).thenReturn(tasks);

        ResultActions response = mockMvc.perform(get("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void ToDoListController_GetTaskById_ReturnTask() throws Exception {
        long taskId = 1;
        when(toDoListService.getTaskById(taskId)).thenReturn(task);

        ResultActions response = mockMvc.perform(get("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void ToDoListController_AddTasks_ReturnAddedItem() throws Exception{

        Task task1 = new Task()
                .setDescription("Eat")
                .setComplete(true);

        when(toDoListService.addTask(ArgumentMatchers.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));


        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void ToDoListController_UpdateTask_UpdateItem() throws Exception {

        Task task1 = new Task()
                .setDescription("Learn java")
                .setComplete(true);

        when(toDoListService.updateTask(ArgumentMatchers.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(put("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void ToDoListController_DeleteTask_DeleteItem() throws Exception {

        long taskId = 1;
        doNothing().when(toDoListService).deleteTaskById(taskId);

        ResultActions response = mockMvc.perform(delete("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    }
