//package com.madmin.policies;
//
//import com.madmin.policies.controller.AdminController;
//import com.madmin.policies.object.FirewallPolicy;
//import com.madmin.policies.repository.DevPoliciesRepository;
//import com.madmin.policies.services.UserService;
//import com.madmin.policies.utils.Role;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.io.IOException;
//import java.nio.file.AccessDeniedException;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//@RunWith(MockitoJUnitRunner.class)
//public class DevPoliciesRepositoryTest {
//    @InjectMocks
//    private AdminController adminController;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private Authentication authentication;
//
//    @Test
//    public void testSavePolicy_AsAdmin_Success() throws IOException {
//        // Arrange
//        String adminUsername = "admin";
//        Set<Role> adminRoles = new HashSet<>(Collections.singletonList(new Role("ADMIN")));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Mockito.when(authentication.getPrincipal()).thenReturn(adminUsername);
//        Mockito.when(userService.getUserRoles(Mockito.eq(adminUsername))).thenReturn(adminRoles);
//
//        // Act
//        FirewallPolicy responseEntity = adminController.saveDefaultPolicy();
//
//        // Assert
//        // Assert that the response entity is successful (e.g., HTTP status 200)
//        Assert.assertTrue(responseEntity.isActive());
//    }
//
//    @Test
//    public void testSavePolicy_AsNonAdmin_Forbidden() throws IOException {
//        // Arrange
//        String nonAdminUsername = "user";
//        Set<Role> nonAdminRoles = new HashSet<>(Collections.singletonList(new Role("USER")));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Mockito.when(authentication.getPrincipal()).thenReturn(nonAdminUsername);
//        Mockito.when(userService.getUserRoles(Mockito.eq(nonAdminUsername))).thenReturn(nonAdminRoles);
//
//
//        try {
//            FirewallPolicy policy = adminController.saveDefaultPolicy();
//            // If the method execution reaches this point, it means the access was granted
//            Assert.fail("Expected AccessDeniedException to be thrown");
//        } catch (AccessDeniedException ex) {
//            // Assert that an AccessDeniedException was thrown, indicating forbidden access
//            Assert.assertNotNull(ex);
//        }
//    }
//}
