package saphy.saphy.bookmark.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.bookmark.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
