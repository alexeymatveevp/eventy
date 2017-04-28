package com.davidluckystar.cntr

import com.davidluckystar.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * Created by david on 3/9/2017.
 */
@RestController
class ImageCtrl {

    @Autowired
    lateinit var imageService: ImageService

    @RequestMapping("/image", method = arrayOf(RequestMethod.POST))
    fun uploadImage(@RequestParam("imageFile")imageFile: MultipartFile): String {
        return imageService.uploadImage(Date(), imageFile.originalFilename, imageFile.bytes)
    }

    @ResponseBody
    @RequestMapping("/image", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.IMAGE_JPEG_VALUE))
    fun getImage(@RequestParam("imageRelativePath") imageRelativePath: String): ResponseEntity<ByteArray> {
        return ResponseEntity.ok(imageService.getImage(imageRelativePath))
    }

    @RequestMapping("/image", method = arrayOf(RequestMethod.DELETE))
    fun deleteImage(@RequestParam("imageRelativePath") imageRelativePath: String) {
        imageService.deleteImage(imageRelativePath)
    }

    @ResponseBody
    @RequestMapping("/userImage", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.IMAGE_JPEG_VALUE))
    fun getUserImage(@RequestParam("user") user: String): ResponseEntity<ByteArray> {
        return ResponseEntity.ok(imageService.getUserImage(user))
    }

}