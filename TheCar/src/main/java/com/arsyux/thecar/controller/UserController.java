package com.arsyux.thecar.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arsyux.thecar.domain.User;
import com.arsyux.thecar.dto.ResponseDTO;
import com.arsyux.thecar.dto.UserDTO;
import com.arsyux.thecar.dto.UserDTO.EmailCheckValidationGroup;
import com.arsyux.thecar.dto.UserDTO.FindUserNameValidationGroup;
import com.arsyux.thecar.dto.UserDTO.InsertUserValidationGroup;
import com.arsyux.thecar.dto.UserDTO.PhoneCheckValidationGroup;
import com.arsyux.thecar.dto.UserDTO.UpdateUserValidationGroup;
import com.arsyux.thecar.dto.UserDTO.UsernameCheckValidationGroup;
import com.arsyux.thecar.security.UserDetailsImpl;
import com.arsyux.thecar.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	
	// ========================================
	// 로그인
	// ========================================
	
	// 로그인 이동
	@GetMapping("/auth/loginUser")
	public String login(@RequestParam(value = "error", required = false)String error,
						@RequestParam(value = "exception", required = false)String exception,
						Model model) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "user/loginUser";
	}
	
	// ========================================
	// 회원가입
	// ========================================
	
	// 회원 가입 이동
	@GetMapping("/auth/insertUser")
	public String insertUser() {
		return "user/insertUser";
	}
	// 회원 가입 기능
	@PostMapping("/auth/insertUser")
	public @ResponseBody ResponseDTO<?> insertUser(@Validated(InsertUserValidationGroup.class) @RequestBody UserDTO userDTO, BindingResult bindingResult) {
		
		// UserDTO를 통해 유효성 검사
		User user = modelMapper.map(userDTO, User.class);
		
		// 중복되는 아이디 검색
		User findUsername = userService.findByUsername(user.getUsername());
		// 중복되는 아이디가 있을 경우 알림 표시
		if(findUsername.getUsername() != null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "이미 회원가입 되어있는 아이디입니다.");	
		}
		
		// 중복되는 휴대폰 검색
		User findUserPhone = userService.findByPhone(user.getPhone());
		// 중복되는 휴대폰이 있을 경우 알림 표시
		if(findUserPhone.getPhone() != null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 휴대폰 번호입니다.");	
		}

		// 중복되는 이메일 검색
		User findUserEmail = userService.findByEmail(user.getEmail());
		// 중복되는 이메일이 있을 경우 알림 표시
		if(findUserEmail.getEmail() != null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일입니다.");	
		}
		
		// 중복 체크 이후 insert
		userService.insertUser(user);
		return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "님 환영합니다!");		
	}
	// 회원 아이디 중복 검사
	@PostMapping("/auth/usernameCheck")
	public @ResponseBody ResponseDTO<?> usernameCheck(@Validated(UsernameCheckValidationGroup.class) @RequestBody UserDTO userDTO, BindingResult bindingResult) {

		// UserDTO를 통해 유효성 검사
		User user = modelMapper.map(userDTO, User.class);
		
		// 중복되는 아이디 검색
		User findUsername = userService.findByUsername(user.getUsername());
		
		// 중복되는 아이디가 있을 경우 알림 표시
		if(findUsername.getUsername() != null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "이미 회원가입 되어있는 아이디입니다.");	
		} else {
			return new ResponseDTO<>(HttpStatus.OK.value(), "사용이 가능한 아이디입니다.\n해당 아이디를 사용하시겠습니까?");	
		}
	}
	// 휴대폰 번호 중복 검사
	@PostMapping("/auth/phoneCheck")
	public @ResponseBody ResponseDTO<?> phoneCheck(@Validated(PhoneCheckValidationGroup.class) @RequestBody UserDTO userDTO, BindingResult bindingResult) {
		
		// 중복되는 휴대폰 검색
		User findUserPhone = userService.findByPhone(userDTO.getPhone());
		// 중복되는 휴대폰이 있을 경우 알림 표시
		if(findUserPhone.getPhone() != null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 휴대폰 번호입니다.");	
		} else {
			return new ResponseDTO<>(HttpStatus.OK.value(), "사용이 가능한 휴대폰 번호입니다.\n해당 휴대폰 번호를 사용하시겠습니까?");	
		}
	}
	// 이메일 중복 검사
	@PostMapping("/auth/emailCheck")
	public @ResponseBody ResponseDTO<?> emailCheck(@Validated(EmailCheckValidationGroup.class) @RequestBody UserDTO userDTO, BindingResult bindingResult) {

		// 중복되는 이메일 검색
		User findUserEmail = userService.findByEmail(userDTO.getEmail());
		// 중복되는 이메일이 있을 경우 알림 표시
		if(findUserEmail.getEmail() != null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일입니다.");	
		} else {
			return new ResponseDTO<>(HttpStatus.OK.value(), "사용이 가능한 이메일입니다.\n해당 이메일을 사용하시겠습니까?");	
		}
		
	}
	
	// ========================================
	// 로그인 아이디, 비밀번호 찾기
	// ========================================
	
	// 로그인 아이디 찾기 이동
	@GetMapping("/auth/findUsername")
	public String findUsername() {
		return "user/findUsername";
	}
	@PostMapping("/auth/findUsername")
	public @ResponseBody ResponseDTO<?> findUsername(@Validated(FindUserNameValidationGroup.class) @RequestBody UserDTO userDTO) {

		// UserDTO를 통해 유효성 검사
		User user = modelMapper.map(userDTO, User.class);
		
		User findUser = userService.findUsername(user);
		
		if(findUser.getUsername() == null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "입력하신 정보에 해당하는 아이디를 찾을 수 없습니다.");
		} else {
			String hideUsername = "";
			if(findUser.getUsername().length() < 4) {
				for(int i=0; i<findUser.getUsername().length(); i++) {
					if(i != findUser.getUsername().length() - 1) {						
						hideUsername += findUser.getUsername().charAt(i);					
					} else {
						hideUsername += "*";	
					}
				}
			} else {
				for(int i=0; i<findUser.getUsername().length(); i++) {
					if(i <= findUser.getUsername().length() - 3) {						
						hideUsername += findUser.getUsername().charAt(i);					
					} else {
						hideUsername += "*";
					}
				}
			}
			return new ResponseDTO<>(HttpStatus.OK.value(), "회원님의 아이디는 " + hideUsername + "입니다.");			
		}
	}
	
	// 비밀번호 찾기 이동
	@GetMapping("/auth/findPassword")
	public String findPassword() {
		return "user/findPassword";
	}
	
	// ========================================
	// 회원 정보 수정
	// ========================================
	
	// 회원 정보 수정 이동
	@GetMapping("/user/updateUser")
	public String updateUser() {
		return "user/updateUser";
	}
	// 회원 정보 수정
	@PutMapping("/user/updateUser")
	public @ResponseBody ResponseDTO<?> updateUser(@Validated(UpdateUserValidationGroup.class) @RequestBody UserDTO userDTO, @AuthenticationPrincipal UserDetailsImpl principal) {

		// UserDTO를 통해 유효성 검사
		User user = modelMapper.map(userDTO, User.class);
		
		// 회원 정보 수정과 동시에 세션을 갱신해야하므로 updateUser()메소드에서 회원 정보를 수정하고 UserService.updateUser() 메소드가 반환한 User 객체로
		// SecurityContext에 등록된 AUthentication 객체의 User를 변경하도록 한다.
		principal.setUser(userService.updateUser(user));
		
		return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "님의 정보가 수정되었습니다.");
	}
	
}
