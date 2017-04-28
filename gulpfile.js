/*
	ITEC (Ignore The Extra Club) Gulpfile for Front End Tasks
	Author: @Scoobter17 Phil Gibbins
 */

'use strict';

/******************************************************************************/

/* GULP CONFIG */

/* App Dependencies --------------------------------------------------------- */

// Gulp
var gulp = require('gulp');

// CSS
var sass = require('gulp-sass');

// Processing
var browserify = require('browserify');

// Utilities
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');

/* Variables ---------------------------------------------------------------- */

var filePaths = {
	allFilesInAllFolders: '**/*',
	css: {
		src: 'src/main/css/',
		dist: 'dist/css/'
	}
};
var fileExtensions = {
	css:  {
		src: '.scss',
		dist: '.css'
	}
};

/******************************************************************************/

/* STYLES */

/**
 * Task to compile Sass into CSS
 */
gulp.task('sass', function() {
	return gulp.src('./' + filePaths.css.src + filePaths.allFilesInAllFolders + fileExtensions.css.src)
		.pipe(sourcemaps.init())
		.pipe(
			sass({
	            includePaths: [
	            	'node_modules/purecss/build'
	            ]
	        }
        ).on('error', sass.logError))
		.pipe(sourcemaps.write())
		.pipe(gulp.dest('./' + filePaths.css.dist));
});

/******************************************************************************/

/* SCRIPTS */

/**
 * Task to compile ES2015+ code into ES5
 */
gulp.task('js', function() {
	return browserify('./src/main/js/app.js')
		.transform('babelify')
		.bundle()
		.pipe(source('app.js'))
		.pipe(buffer())
		.pipe(uglify())
		.pipe(gulp.dest('./dist/js'));
});

/******************************************************************************/
