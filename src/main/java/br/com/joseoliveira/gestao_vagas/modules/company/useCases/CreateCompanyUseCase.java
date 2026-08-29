package br.com.joseoliveira.gestao_vagas.modules.company.useCases;

import br.com.joseoliveira.gestao_vagas.exceptions.UserFoundException;
import br.com.joseoliveira.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.joseoliveira.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository
                //Pesquisa se o cadastro da empresa já existe, se existe é lançada exceção
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        //faz o encode da senha
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }
}