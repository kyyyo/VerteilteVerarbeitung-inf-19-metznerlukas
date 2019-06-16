package vvproject.restful.Server.Member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository stores all user accounts
 * @author Lukas Metzner, sINFlumetz
 */
@Repository("MemberRepository")
public interface MemberRepository extends CrudRepository<Member, String> {
}
