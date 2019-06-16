package vvproject.restful.Server.Clothing;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;
import vvproject.restful.Server.Member.MemberService;
import vvproject.restful.Server.Transaction.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clothing service handles the access for the
 * clothing database. -> Resolving Optional<Clothing>
 * @author Lukas Metzner, sINFlumetz
 */
@Service("ClothingService")
public class ClothingService {
    private ClothingRepository clothingRepository;
    private MemberService memberService;
    private TransactionService transactionService;

    @Autowired
    public ClothingService(ClothingRepository clothingRepository, MemberService memberService, TransactionService transactionService) {
        this.clothingRepository = clothingRepository;
        this.memberService = memberService;
        this.transactionService = transactionService;
    }

    public List<Clothing> findAll() {
        List<Clothing> allClothings = new ArrayList<>();
        this.clothingRepository.findAll().forEach((clothing) -> allClothings.add(clothing));
        return allClothings;
    }

    public void saveClothing(Clothing c) {
        this.clothingRepository.save(c);
    }

    public Clothing findById(Long id) throws ClothingNotFoundException {
        Optional<Clothing> clothing = this.clothingRepository.findById(id);
        if (clothing.isPresent()) {
            return clothing.get();
        } else {
            throw new ClothingNotFoundException(id + " Not found!");
        }
    }

    public void updateClothing(Clothing c) {
        this.clothingRepository.save(c);
    }
}
