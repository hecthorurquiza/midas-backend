package ifg.midas.domain.group;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.group.dto.GroupRegistryDTO;
import ifg.midas.domain.group.dto.GroupUpdateDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "groups_clients",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Client> members;

    public Group(GroupRegistryDTO registryDTO, Set<Client> members) {
        this.name = registryDTO.name().toUpperCase();
        this.description = registryDTO.description().toLowerCase();
        this.members = members;
    }

    public void updateInfos(GroupUpdateDTO updateDTO, Set<Client> members) {
        Optional.ofNullable(updateDTO.name()).ifPresent(name -> { if (!name.isBlank()) setName(name.toUpperCase()); });
        Optional.ofNullable(updateDTO.description()).ifPresent(desc -> { if (!desc.isBlank()) setDescription(desc.toLowerCase()); });
        Optional.ofNullable(members).ifPresent(m -> { if (!m.isEmpty()) setMembers(m); });
    }
}
