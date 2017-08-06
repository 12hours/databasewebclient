'use strict';

var gulp = require('gulp'),
    minifycss = require('gulp-minify-css'),
    jshint = require('gulp-jshint'),
    stylish = require('jshint-stylish'),
    uglify = require('gulp-uglify'),
    usemin = require('gulp-usemin'),
    imagemin = require('gulp-imagemin'),
    rename = require('gulp-rename'),
    concat = require('gulp-concat'),
    notify = require('gulp-notify'),
    cache = require('gulp-cache'),
    changed = require('gulp-changed'),
    rev = require('gulp-rev'),
    browserSync = require('browser-sync'),
    del = require('del'),
    url = require('url'),
    proxy = require('proxy-middleware'),
    fileinclude = require('gulp-file-include')
;

gulp.task('jshint', function () {
    return gulp.src('app/scripts/**/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter(stylish));
});

// Clean
gulp.task('clean', function () {
    return del(['dist']);
});

// Default task
gulp.task('default', ['clean'], function () {
    gulp.start('usemin', 'imagemin', 'copyScripts', 'copyContent', 'copyStyles', 'copyfonts');
});

gulp.task('build', function () {
    gulp.start('copyScripts', 'copyContent', 'copyStyles', 'copyfonts');
});

gulp.task('usemin', ['jshint'], function () {
    return gulp.src('./app/index.html')
        .pipe(usemin({
            css: [minifycss(), rev()],
            js: [uglify(), rev()]
        }))
        .pipe(gulp.dest('dist/'));
});

// Images
gulp.task('imagemin', function () {
    return del(['dist/images']), gulp.src('app/images/**/*')
        .pipe(cache(imagemin({ optimizationLevel: 3, progressive: true, interlaced: true })))
        .pipe(gulp.dest('dist/images'))
        .pipe(notify({ message: 'Images task complete' }));
});

gulp.task('copyfonts', function () {
    gulp.src('app/fonts/**/*.{ttf,woff,eof,svg}*')
        .pipe(gulp.dest('./dist/fonts'));
});

gulp.task('copyScripts', function () {
    gulp.src('app/js-libs/*')
        .pipe(gulp.dest('./dist/js'));
        gulp.src('app/scripts/*.js')
        .pipe(gulp.dest('dist/js'));
});

gulp.task('copyContent', function () {
    gulp.src('app/*.html')
        .pipe(fileinclude({
            prefix: '@@',
            basepath: '@file'
        }))
        .pipe(gulp.dest('dist'));
});

gulp.task('copyStyles', function () {
    gulp.src('app/css/*.css')
        .pipe(gulp.dest('dist/css'));
});

// Watch
gulp.task('watch', ['browser-sync'], function () {
    // Watch .js files
    gulp.watch('{app/scripts/**/*.js,app/css/**/*.css,app/**/*.html}', ['usemin']);
    // Watch image files
    gulp.watch('app/images/**/*', ['imagemin']);

});

gulp.task('browser-sync', ['default'], function () {
    var files = [
        'app/**/*.html',
        'app/css/**/*.css',
        'app/images/**/*.png',
        'app/scripts/**/*.js',
        'dist/**/*'
    ];

    browserSync.init(files, {
        server: {
            baseDir: "dist",
            index: "index.html"
        }
    });
    // Watch any files in dist/, reload on change
    gulp.watch(['dist/**']).on('change', browserSync.reload);
});

gulp.task('serve', function() {
    gulp.watch('{app/scripts/**/*.js,app/js-libs/**/*.js}', ['copyScripts']);
    gulp.watch('{app/css/**/*.js}', ['copyStyles']);
    gulp.watch('{app/content/**/*.html,app/*.html}', ['copyContent']);

    var files = [
        'app/**/*.html',
        'app/css/**/*.css',
        'app/images/**/*.png',
        'app/scripts/**/*.js',
        'dist/**/*'
    ];

    var proxyOptions = url.parse('http://localhost:8080/api');
    proxyOptions.route = '/api';
    browserSync.init({
        notify: false,
        logPrefix: 'WSK',
        server: {
            baseDir: "dist",
            index: 'index.html',
            middleware: [proxy(proxyOptions)]
        },
        port: 3000
    });
    gulp.watch(['dist/**']).on('change', browserSync.reload);
});
