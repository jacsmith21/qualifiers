package ca.unb.qualifiers.service;

import ca.unb.qualifiers.exception.InternalServerErrorException;
import ca.unb.qualifiers.exception.NotFoundException;
import ca.unb.qualifiers.model.Deliverable;
import ca.unb.qualifiers.model.Upload;
import ca.unb.qualifiers.repository.SubmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private static final Logger LOG = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    @Autowired
    SubmissionRepository submissionRepository;

    @Override
    public void add(Deliverable deliverable, MultipartFile file) throws IOException {
        Upload upload = new Upload();
        upload.setName(file.getOriginalFilename());
        upload.setData(file.getBytes());

        deliverable.getUploads().add(upload);
        submissionRepository.save(deliverable);
    }

    @Override
    public Deliverable load(String filename) {
        List<Deliverable> deliverables = submissionRepository.findByName(filename);
        if(deliverables.size() == 0) {
            LOG.warn("there are no files named {}", filename);
            throw new NotFoundException();
        }

        if(deliverables.size() > 1) {
            LOG.warn("there are too many file named {} -> {}", filename, deliverables.size());
            throw new InternalServerErrorException();
        }

        return deliverables.get(0);
    }

    @Override
    public Iterable<Deliverable> loadAll() {
        return null;
    }
}
