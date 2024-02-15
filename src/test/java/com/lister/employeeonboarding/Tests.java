package com.lister.employeeonboarding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lister.employeeonboarding.controller.EmployeeController;
import com.lister.employeeonboarding.controller.HrController;
import com.lister.employeeonboarding.controller.LoginController;
import com.lister.employeeonboarding.entity.Address;
import com.lister.employeeonboarding.entity.EmployeeDemographics;
import com.lister.employeeonboarding.entity.Role;
import com.lister.employeeonboarding.entity.User;
import com.lister.employeeonboarding.exception.EmployeeNotFoundException;
import com.lister.employeeonboarding.exception.InvalidException;
import com.lister.employeeonboarding.repository.EmployeeDemographicsRepository;
import com.lister.employeeonboarding.repository.RoleRepository;
import com.lister.employeeonboarding.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.lister.employeeonboarding.service.EmployeeDemographicsService;
import com.lister.employeeonboarding.service.HrService;
import com.lister.employeeonboarding.service.LoginService;
import com.lister.employeeonboarding.service.MailSenderService;
import com.lister.employeeonboarding.voobject.EmployeeRequestVo;
import com.lister.employeeonboarding.voobject.EmployeeDemographicsVo;
import com.lister.employeeonboarding.voobject.StatusInfo;
import com.lister.employeeonboarding.voobject.UserVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class Tests {

    @InjectMocks
    MailSenderService emailService;
    @InjectMocks
    private HrController hrController;
    @InjectMocks
    private EmployeeController employeeController;
    @InjectMocks
    private LoginController loginController;
    @Mock
    JavaMailSender mailSender;
    @Mock
    Role role;
    @Mock
    HrService hrService1;

    @InjectMocks
    HrService hrService;
    @InjectMocks
    LoginService loginService;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    EmployeeDemographicsService employeeDemographicsService;
    @Mock
    MailSenderService mailSenderService;

    @Mock
    EmployeeDemographicsRepository employeeDemographicsRepository;

    @Mock
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMailTest() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("indraneel316@gmail.com");
        message.setTo("indraneel316@gmail.com");
        message.setSubject("");
        message.setText("");

        emailService.sendSimpleEmail("indraneel316@gmail.com", "Approved", "", UUID.randomUUID());
        verify(mailSender, times(1)).send(message);
    }

    @Test
    public void testForRoleIdAllocation() {
        Role role = new Role(1, "Software Engineer");
        when(roleRepository.findByRoleName("Software Engineer")).thenReturn(role);

        int actual = hrService.roleIdGenerator("Software Engineer");
        assertEquals(1, actual);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Address> addressList = new ArrayList<>();
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, 1, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", "", addressList);
        EmployeeDemographics employeeDemographics1 = new EmployeeDemographics(34, "LIS2110", "Indraneel", "S", "30/03/2000", "A1+ve", "84934193878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, 1, "Male", "Neeel", "Friend", "384554353", null, null, "Pending", "", addressList);
        List<EmployeeDemographics> users = new ArrayList<>();
        users.add(employeeDemographics);
        users.add(employeeDemographics1);
        when(employeeDemographicsRepository.findAll()).thenReturn(users);
        assertEquals(users, hrService.allEmployeeInfo(UUID.randomUUID()));
    }

    @Test
    public void saveEmployeeDataTest() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        addressList.add(address2);
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, 1, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", "", addressList);

        EmployeeDemographicsVo employeeDemographicsVo = new EmployeeDemographicsVo(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", addressList, "");
        when(employeeDemographicsRepository.findByEmployeeId(33)).thenReturn(employeeDemographics);
        when(employeeDemographicsRepository.save(employeeDemographics)).thenReturn(employeeDemographics);
        UUID uuid = UUID.randomUUID();
        employeeDemographicsService.updateDetails(employeeDemographicsVo, 33, uuid);
        verify(employeeDemographicsRepository, times(1)).save(employeeDemographics);

    }

    @Test
    public void TestForGetEmployeeById() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        addressList.add(address2);
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, 1, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", "", addressList);
        when(employeeDemographicsRepository.findByEmployeeId(33)).thenReturn(employeeDemographics);
        UUID uuid = UUID.randomUUID();
        assertEquals(employeeDemographics, employeeDemographicsService.fetchDetails(33, uuid));


    }

    @Test
    public void testForCheckEmployee() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, 1, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", "", addressList);

        when(employeeDemographicsRepository.findByEmployeeId(33)).thenReturn(employeeDemographics);
        assertEquals("indraneel316@gmail.com", hrService.getEmployeeEmailById(33, UUID.randomUUID()));
    }

    @Test
    public void testForStatusUpdate() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "", 10.0, 7.0, 7.0, 1, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", "", addressList);

        when(employeeDemographicsRepository.findByEmployeeId(33)).thenReturn(employeeDemographics);

        StatusInfo status = new StatusInfo();
        status.setStatusType("Approve");
        status.setRejectReason("");

        assertEquals(true, hrService.updateStatus(status, 33, UUID.randomUUID()));

    }

    @Test
    public void testForStatusUpdateDecline() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "", 10.0, 7.0, 7.0, 1, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", "", addressList);

        when(employeeDemographicsRepository.findByEmployeeId(33)).thenReturn(employeeDemographics);

        StatusInfo status = new StatusInfo();
        status.setStatusType("Reject");
        status.setRejectReason("Invalid Aadhar Number");

        assertEquals(false, hrService.updateStatus(status, 33, UUID.randomUUID()));

    }

    @Test
    public void testForCheckLogin() {
        ArrayList<String> loginInfo = new ArrayList<>();
        loginInfo.add("33");
        loginInfo.add("1");
        loginInfo.add("Pending");
        loginInfo.add("updated user");
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, 1, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", "", addressList);
        User user = new User();
        user.setEmailId("indraneel316@gmail.com");
        user.setRoleId(1);
        user.setPassword("Abc1235@");
        user.setUserId(employeeDemographics);
        Role role = new Role();
        role.setRoleId(3);
        role.setRoleName("HR");
        when(userRepository.findByEmailIdAndPassword("indraneel316@gmail.com", "Abc1235@fd")).thenReturn(user);

        when(roleRepository.findByRoleName("HR")).thenReturn(role);
        ArrayList<String> arr2 = (ArrayList<String>) loginService.loginInfo("indraneel316@gmail.com", "Abc1235@fd", UUID.randomUUID());
        assertEquals(loginInfo, arr2);

    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testForFetchDetailsException() {
        UUID uuid = UUID.randomUUID();
        employeeDemographicsService.fetchDetails(30, uuid);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testForUpdateDetailsException() {
        List<Address> addressList = new ArrayList<>();
        Address presentAddress = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(presentAddress);
        EmployeeDemographicsVo employeeDemographicsVo = new EmployeeDemographicsVo(33, "LIS2100", "Indraneel", "S", "30/03/2000", "A1+ve", "84934893878", "XYZ", "XYZZ", "indraneel316@gmail.com", "5738495738454", 10.0, 7.0, 7.0, "Male", "Neel", "Friend", "94384554353", null, null, "Pending", addressList, "");
        UUID uuid = UUID.randomUUID();
        employeeDemographicsService.updateDetails(employeeDemographicsVo, 33, uuid);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testForInviteHire() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        EmployeeDemographics employeeDemographics = new EmployeeDemographics(0, "LIS1", "Neel", "Neel", null, null, null, null, null, "indraneel316@gmail.com", null, null, null, null, 1, null, null, null, null, null, null, "INCOMPLETE", "", null);
        List<EmployeeDemographics> employees = new ArrayList<>();
        EmployeeRequestVo employeeRequestVo = new EmployeeRequestVo();
        employeeRequestVo.setEmail("indraneel316@gmail.com");
        employeeRequestVo.setName("Neel");
        employeeRequestVo.setPassword("Abc1235@");
        employeeRequestVo.setRole(1);
        employees.add(employeeDemographics);
        hrService.createUser(employeeRequestVo, UUID.randomUUID());


    }

    @Test(expected = InvalidException.class)
    public void testForRoleGenerator() {
        hrService.roleIdGenerator(null);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testForStatusUpdateException() {
        StatusInfo status = new StatusInfo();
        status.setStatusType("Approved");
        status.setRejectReason("");
        hrService.updateStatus(status, 33, UUID.randomUUID());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testForCheckEmployeeException() {
        hrService.getEmployeeEmailById(33, UUID.randomUUID());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testForCheckLoginException() {
        loginService.loginInfo("indraneel316@gmail.com", "dsfsdfsdf", UUID.randomUUID());
    }


    @Test
    public void controllerStatusUpdateTest() throws Exception {
        hrController = mock(HrController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
        StatusInfo requestBody = new StatusInfo();
        requestBody.setStatusType("Approve");
        requestBody.setId(455);
        requestBody.setRejectReason("");
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(requestBody);
        mockMvc.perform(put("/user/status")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        verify(hrController, times(1)).updateUserStatus(requestBody);
    }

    @Test
    public void controllerTestForSubmitUser() throws Exception {
        employeeController = mock(EmployeeController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        Address address2 = new Address(1, "LIS9947", "", "fjdskkj", "fjdshfds", "fsdfkjdsf", "Chennai", "India", "Chennai", "60854", "kfjsdkf");
        addressList.add(address1);
        EmployeeDemographicsVo requestBody = new EmployeeDemographicsVo(33, "LIS10", "Indraneel", "S", "30/03/2000", "A+ve", "888888888888", "XYZ", "ABC", "indraneel316@gmail.com", "9464565454", 80.0, 80.0, 8.0, "Male", null, null, "6456546544", null, null, null, addressList, "Submit");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/details/33")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        verify(employeeController, times(1)).submitData(33, requestBody);


    }

    @Test
    public void controllerTestForCreateUser() throws Exception {
        hrController = mock(HrController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
        EmployeeRequestVo employeeRequestVo = new EmployeeRequestVo();
        employeeRequestVo.setEmail("indraneel316@gmail.com");
        employeeRequestVo.setName("Indraneel");
        employeeRequestVo.setRole(1);
        employeeRequestVo.setPassword("Abc12345@fd");
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(employeeRequestVo);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(employeeRequestVo)))
                .andExpect(status().isOk());
        verify(hrController, times(1)).createUser(employeeRequestVo);

    }

    @Test
    public void controllerTestForGetAllEmployees() throws Exception {

        hrController = mock(HrController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(hrController, times(1)).getAllEmployeeDetails();
    }

    @Test
    public void controllerTestForGetAllRoles() throws Exception {

        hrController = mock(HrController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk());

        verify(hrController, times(1)).getAllRoles();
    }

    @Test
    public void controllerTestForNotification() throws Exception {
        hrController = mock(HrController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
        mockMvc.perform(post("/user/33/notification")
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isOk());
        verify(hrController, times(1)).sendNotification(33);

    }

    @Test
    public void controllerTestForFetchEmployee() throws Exception {
        employeeController = mock(EmployeeController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        mockMvc.perform(get("/details/33")
                .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isOk());
        verify(employeeController, times(1)).getEmployeeDataById(33);

    }

    @Test
    public void testForLoginController() throws Exception {
        loginController = mock(LoginController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        UserVo requestBody = new UserVo();
        requestBody.setEmailId("indraneel316@gmail.com");
        requestBody.setPassword("Abc12345@fd");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        verify(loginController, times(1)).userLogin(requestBody);
    }


}







