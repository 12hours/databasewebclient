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

gulp.task('build', function () {
    gulp.start('copyScripts', 'copyContent', 'copyStyles', 'copyLibs');
});

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

// Images
// gulp.task('imagemin', function () {
//     return del(['dist/images']), gulp.src('app/images/**/*')
//         .pipe(cache(imagemin({optimizationLevel: 3, progressive: true, interlaced: true})))
//         .pipe(gulp.dest('dist/images'))
//         .pipe(notify({message: 'Images task complete'}));
// });

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
        .pipe(gulp.dest('./dist/js'))

});

gulp.task('copyScripts', function () {
    gulp.src('app/scripts/*.js')
        .pipe(fileinclude({
            prefix: '@@',
            basepath: '@file'
        }))
        .pipe(gulp.dest('./dist/js/'));
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
