package org.example.board_login_in_webtoken.security.services;

import lombok.RequiredArgsConstructor;
import org.example.board_login_in_webtoken.model.entity.auth.User;
import org.example.board_login_in_webtoken.repository.UserRepository;
import org.example.board_login_in_webtoken.security.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * packageName : org.example.board_login_in_webtoken.security.services
 * fileName : UserDetailServiceImpl
 * author : hayj6
 * date : 2024-05-22(022)
 * description :
 * 요약 : TODO : DB 인증을 담담하는 클래스
 * TODO 로그인할때 ID,PW 받음. DB에 들어가있는상태. 우리 DB에 있는지 확인하는 절차 = DB 인증. 있으면 우리 유저라서 인증된 유저
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22(022)         hayj6          최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //    함수 재정의 : 자동 기능 : alt + insert
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        TODO: 1) 유저 DB 인증 : 상세조회
//          js 화살표 함수 : function(x){return x+1;} : (x) => x + 1;
//          java 람다식   : interface x(함수())       : (x) -> x + 1;
//         사용법 : 자료형 변수 = 옵셔널객체.orElseThrow(new 예외처리클래스("에러메세지"));
//            => 옵셔널객체의 결과가 null 이면 에러메세지 출력, 아니면 변수에 저장됨
        User user
                = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("ID 없음:" + userId));

//        TODO: 2) 검증객체에 정보 넣기
//               2-1) 권한을 생성해서 넣기 : GrantedAuthority(스프링시큐리티 권한클래스)
//                 => codeName : 권한명 (ROLE_ADMIN, ROLE_USER) 스프링 시큐리티가 ROLE_이 붙어야만 인식을 한다.
//                 => 사용법 : GrantedAuthority 변수 = new SimpleGrantedAuthority(권한명);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole()); // 유저,관리자에 대한 권한이 있음

//        TODO:  2-2) 권한 배열에(List, Set 등) 넣기 : Set 에 넣기
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);    // Set 배열에 권한 넣기
//        스프링 시튜리티의 권한은 배열로 되어있음(집합)
//        권한관리 복잡할수동 있음
//        A는 [관리자+조정자]
//        B는 [유저]
//        우리는 한사람에 한 권한만 가지게했었음. 배열이지만!
//        A는 [관리자], B는 [유저]. 빼내려면 인덱스번호로 빼기. 한사람에 한권한이라서 인덱스번호 0

//        생성자 : (userId, password, 권한배열)
//        우리가 권한을 하나만 쓰지만, 권한들로 관리한다. 이렇게 설계해놨기 때문에 울우리는 배열로 쓴다.
        return new UserDto(user.getUserId(),
                user.getPassword(),
                authorities
        );
    }
}
