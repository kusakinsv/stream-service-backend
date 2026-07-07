//package ru.one.stream.server.controller.rest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import ru.one.stream.server.dto.UserTestDto;
//import ru.one.stream.server.service.UserService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@RestController
//@RequestMapping("api/v1/test")
//public class TestController {
//
//    @Autowired
//    UserService userService;
//
//    private List<UserTestDto> USERS = Stream.of(
//            new UserTestDto(1L, "Ivan"),
//            new UserTestDto(2L, "Sergei"),
//            new UserTestDto(3L, "Petr")
//    ).collect(Collectors.toList());
//
//    @GetMapping("/getUserspace/{username}")
//    public ResponseEntity<?> getUserSpace(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserspaceByUsername(username));
//    }
//
//    @GetMapping("admin")
//    public String admin(){
//        return "admin ok";
//    }
//
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public UserTestDto getById(@PathVariable Long id){
//        return USERS.stream().filter(developer -> developer.getId().equals(id))
//                .findFirst().orElse(null);
//    }
//
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public List<UserTestDto> create(@RequestBody UserTestDto user){
//        this.USERS.add(user);
//        return USERS;
//    }
//
//    @DeleteMapping("/{id}")
////    @PreAuthorize("hasAuthority('developers:write')")
//    public void deleteById(@PathVariable Long id){
//        this.USERS.removeIf(developer -> developer.getId().equals(id));
//    }
//
//}
//
