package vvproject.restful.Server.Member;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vvproject.restful.Server.Member.MemberExceptions.MemberNotFoundException;
import vvproject.restful.Server.Member.MemberExceptions.WrongLoginException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service("MemberService")
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(String id) throws MemberNotFoundException {
        Optional<Member> optionalMember = this.memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        } else {
            throw new MemberNotFoundException(id + " Not found!");
        }
    }

    public void saveMember(Member m) {
        this.memberRepository.save(m);
    }

    public void updateMember(Member m) throws MemberNotFoundException {
        Member toUpdate = this.findById(m.getUsername());
        this.memberRepository.save(m);
    }

    public Member login(String username, String password) throws MemberNotFoundException, WrongLoginException {
        Member found = this.findById(username);
        if (found == null)
            throw new WrongLoginException("Wrong password");
        if (found.getPassword().equals(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString())) {
            return found;
        } else {
            throw new WrongLoginException("Wrong password");
        }
    }
}
