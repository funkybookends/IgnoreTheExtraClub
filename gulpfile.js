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

// Utilities
var sourcemaps = require('gulp-sourcemaps');

/* Variables ---------------------------------------------------------------- */

var filePaths = {
	allFilesInAllFolders: '**/*',
	css: {
		src: 'src/css/',
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