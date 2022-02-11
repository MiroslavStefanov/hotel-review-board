function fillRow(data) {
    let row = $('.hotel-row').first().clone();
    let template = $('.hotel-body').first().clone();
    row.empty();
    data._embedded.hotels.forEach((t, i) => {
        let to = t.toDestination;
        let from = t.fromDestination;
        let publisher = t.publisher;
        let current = template.clone();
        let currentCarouselId = 'carousel'+i.toString();
        current.find('.hotel-text').text(to.name);
        current.find('.hotel-start').text('From: ' +  from.name);
        current.find('.hotel-publisher').text('by '+publisher);
        current.find('a').attr('href', '/hotels/' + t.id);

        let addCurrentCarousel = images => {
            addCarousel(current.find('.carousel-container'), images, currentCarouselId);
        };

        let callback = (photos) => {
            saveDestinationImages(photos, to.id, addCurrentCarousel)
        };

        if (to.imageUrls === undefined || to.imageUrls === null || to.imageUrls.length === 0) {
            mapService.getPhotosByGeocode(to.latitude, to.longitude, callback);
        } else {
            addCarousel(current.find('.carousel-container'), to.imageUrls, currentCarouselId);
        }

        row.append(current);
    });
    return row;
}