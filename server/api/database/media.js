'use stric';

const imageSize = require('image-size');

module.exports = (connection) => {

    const module = {};

    module.uploadFile = async(req, res,next) => {

		if (req.user && req.file) {

			try {
				/* Get image dimension because width/height ratio is used in frontend */
				const dimension = imageSize(req.file.path);
				const imageRatio = +(dimension.width / dimension.height).toFixed(2);
				const query = 'INSERT INTO media (filename, path, mimetype, encoding, image_ratio) VALUES(?, ?, ?, ?, ?)';
				const queryParams = [req.file.filename, req.file.path.replace(/\\/g, '/'), req.file.mimetype, req.file.encoding, imageRatio];
				const [rows, _] = await connection.execute(query,queryParams);
				req.insertedFile = {'rows': rows, error: false};
				next();
			} catch (error) {
				res.status(401).json(error);
			}
		} else {
			req.insertedFile = {message: 'Unautherized. Authentication required or file not uploaded.', error: true};
			next();
			// res.status(401).json({message:'Unautherized. Authentication required or file not uploaded.'});
		}
    };
    
    return module;
}