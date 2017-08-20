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
    fileinclude = require('gulp-file-include'),
    url = require('url'),
    proxy = require('proxy-middleware'),
    inject = require('gulp-inject')
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

gulp.task('build', function () {
    gulp.start('copyScripts', 'copyContent', 'copyStyles', 'copyLibs');
});

gulp.task('install', ['build'], function () {
    gulp.src('./dist/**/*')
        .pipe(gulp.dest('../src/main/resources/static'))
})

gulp.task('default', function () {
    gulp.start('clean', 'build');
})

gulp.task('usemin', ['jshint'], function () {
    return gulp.src('./app/index.html')
        .pipe(usemin({
            css: [minifycss(), rev()],
            js: [uglify(), rev()]
        }))
        .pipe(gulp.dest('dist/'));
});

gulp.task('copyLibs', function () {
    gulp.src('bower_components/bootstrap/dist/fonts/**/*.{ttf,woff,eof,svg}*')
        .pipe(gulp.dest('./dist/fonts'));
    gulp.src('bower_components/bootstrap/dist/css/bootstrap.min.css')
        .pipe(gulp.dest('./dist/css'));
    gulp.src('bower_components/bootstrap/dist/js/bootstrap.min.js')
        .pipe(gulp.dest('./dist/js'));
    gulp.src('bower_components/angular/angular.min.js')
        .pipe(gulp.dest('./dist/js'));
    gulp.src('bower_components/jquery/dist/jquery.min.js')
        .pipe(gulp.dest('./dist/js'));
    gulp.src('bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css')
        .pipe(gulp.dest('./dist/css'));
    gulp.src('bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js')
        .pipe(gulp.dest('./dist/js'));
    gulp.src('bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.ru.js')
        .pipe(gulp.dest('./dist/js'));
});

gulp.task('copyScripts', function () {
    gulp.src('app/scripts/*.js')
        .pipe(gulp.dest('./dist/js/'));
});

gulp.task('copyContent', function () {
    gulp.src('app/*.html')
        .pipe(fileinclude({
            prefix: '@@',
            basepath: '@file'
        }))
        .pipe(inject(gulp.src('./app/scripts/*.js', {read: false}), {ignorePath: '/app/scripts/', addPrefix: '/js'}))
        .pipe(gulp.dest('./dist'));
});

gulp.task('copyStyles', function () {
    gulp.src('app/css/*.css')
        .pipe(gulp.dest('dist/css'));
});

gulp.task('serve', ['build'], function () {
    gulp.watch('app/css/**/*.css', ['copyStyles']);
    gulp.watch('app/scripts/*.js', ['copyScripts']);
    gulp.watch(['app/content/*.html', 'app/*.html'], ['copyContent']);

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
