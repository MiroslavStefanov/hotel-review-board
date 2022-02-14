function fillRow(data) {
    let row = $('.hotel-row').first().clone();
    let template = $('.hotel-body').first().clone();
    row.empty();
    data.content.forEach((t, i) => {
        // let id = t.id;
        // let name = t.name;
        // let reviews = t.reviews;
        let current = template.clone();
        let currentCarouselId = 'carousel'+i.toString();
        current.find('.hotel-text').text(t.name);
        // current.find('.hotel-start').text(t.name);
        // current.find('.hotel-publisher').text('by '+publisher);
        current.find('a').attr('href', '/hotels/details/' + t.id);

        // let addCurrentCarousel = images => {
        //     addCarousel(current.find('.carousel-container'), images, currentCarouselId);
        // };
        //
        // let callback = (photos) => {
        //     saveDestinationImages(photos, to.id, addCurrentCarousel)
        // };
        //
        // if (to.imageUrls === undefined || to.imageUrls === null || to.imageUrls.length === 0) {
        //     mapService.getPhotosByGeocode(to.latitude, to.longitude, callback);
        // } else {
        //     addCarousel(current.find('.carousel-container'), to.imageUrls, currentCarouselId);
        // }

        row.append(current);
    });
    return row;
}

function rowResponseHandler(data) {
    let section = $('#all-hotels');
    let row = fillRow(data);
    section.empty();
    section.append(row);
}