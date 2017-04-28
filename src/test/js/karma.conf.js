/**
 * Karma Testing config file
 */
module.exports = function(config) {
    config.set({

        basePath: '',

        browsers: ['PhantomJS'],

        files: [
            'src/test/js/es5/*.js'
        ],

        frameworks: ['jasmine'],

        reporters: ['spec']

    });
};