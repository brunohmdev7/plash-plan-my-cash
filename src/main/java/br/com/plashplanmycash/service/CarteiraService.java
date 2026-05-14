package br.com.plashplanmycash.service;

import br.com.plashplanmycash.domain.dto.AtualizarCarteiraDto;
import br.com.plashplanmycash.domain.dto.CadastroCarteiraDto;
import br.com.plashplanmycash.domain.entity.Carteira;
import br.com.plashplanmycash.domain.entity.Usuario;
import br.com.plashplanmycash.repository.CarteiraRepository;
import br.com.plashplanmycash.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final UsuarioRepository usuarioRepository;

    public Carteira salvar(CadastroCarteiraDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (carteiraRepository.existsByUsuarioIdAndApelido(dto.usuarioId(), dto.apelido()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma carteira com esse apelido");

        Carteira carteira = new Carteira();
        carteira.setUsuario(usuario);
        carteira.setApelido(dto.apelido());
        carteira.setTipoCarteira(dto.tipoCarteira());
        carteira.setTipoConta(dto.tipoConta());
        carteira.setTipoMoeda(dto.tipoMoeda());
        carteira.setSaldo(dto.saldo());

        return carteiraRepository.save(carteira);
    }

    public List<Carteira> listar() { return carteiraRepository.findAll(); }

    public Carteira atualizar(Long id, AtualizarCarteiraDto dto) {
        Carteira carteira = carteiraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carteira não encontrada"));

        if (dto.apelido() != null && !dto.apelido().isBlank()) {
            if (carteiraRepository.existsByUsuarioIdAndApelidoAndIdNot(carteira.getUsuario().getId(), dto.apelido(), id))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma carteira com esse apelido");
            carteira.setApelido(dto.apelido());
        }
        if (dto.tipoCarteira() != null) carteira.setTipoCarteira(dto.tipoCarteira());
        if (dto.tipoConta() != null) carteira.setTipoConta(dto.tipoConta());
        if (dto.tipoMoeda() != null) carteira.setTipoMoeda(dto.tipoMoeda());
        if (dto.saldo() != null) carteira.setSaldo(dto.saldo());

        return carteiraRepository.save(carteira);
    }

    public void deletar(Long id) {
        if (!carteiraRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carteira não encontrada");
        carteiraRepository.deleteById(id);
    }
}