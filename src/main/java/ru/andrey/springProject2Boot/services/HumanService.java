package ru.andrey.springProject2Boot.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrey.springProject2Boot.models.Book;
import ru.andrey.springProject2Boot.models.Human;
import ru.andrey.springProject2Boot.repositories.HumanRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HumanService {
    HumanRepository humanRepository;

    public HumanService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }


    public List<Human> getPeople() {
        return humanRepository.findAll();
    }

    @Transactional
    public void add(Human human) {
        humanRepository.save(human);
    }

    public Human getHuman(int id) {

        Human human = humanRepository.findById(id).orElse(null);

        if (human == null) return null;

        Date currentDate = new Date();
        for (Book book : human.getBooks()) {

            if (currentDate.getTime() - book.getData_of_taken().getTime() > 864000000) book.setOverdue(true);
            else book.setOverdue(false);
        }


        return human;
    }

    @Transactional
    public void update(Human human, int id) {

        Human humanToBeUpdated = humanRepository.findById(id).get();

        human.setId(humanToBeUpdated.getId());
        human.setBooks(humanToBeUpdated.getBooks());

        humanRepository.save(human);
    }

    public Optional<Human> getHuman(String fio) {
        return humanRepository.findByFio(fio);
    }
}
