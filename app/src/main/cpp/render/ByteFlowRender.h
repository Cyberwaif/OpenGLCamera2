//
// Created by ByteFlow on 2019/4/15.
//

#ifndef OPENGLCAMERA2_BYTEFLOWRENDER_H
#define OPENGLCAMERA2_BYTEFLOWRENDER_H

#include <SyncLock.h>
#include "ByteFlowDef.h"
#include "ImageDef.h"

class ByteFlowRender
{
public:
	ByteFlowRender():
	m_ViewportHeight(0),
	m_ViewportWidth(0),
	m_IsProgramChanged(false),
	m_IsTextureDirty(false)
	{
		memset(&m_RenderFrame, 0, sizeof(NativeImage));
	}

	virtual ~ByteFlowRender()
	{

	}

	virtual int Init(int initType) = 0;
	virtual int UnInit() = 0;

    virtual void UpdateFrame(NativeImage *pImage) = 0;

	virtual void LoadFilterImageData(int index, NativeImage *pImage) = 0;

	virtual void LoadFragShaderScript(int shaderIndex, char *pShaderStr, int strLen) = 0;

    virtual void SetTransformMatrix(float translateX, float translateY, float scaleX, float scaleY, int degree, int mirror) = 0;

    virtual void SetHSVColorFilter(float hsv) = 0;

    virtual void SetShaderIndex(int shaderIndex) = 0;

    virtual int GetShaderIndex() = 0;

    virtual bool CreateTextures() = 0;

    virtual bool UpdateTextures() = 0;

    virtual bool DeleteTextures() = 0;

    virtual void OnSurfaceCreated() = 0;

    virtual void OnSurfaceChanged(int width, int height) = 0;

    virtual void OnDrawFrame() = 0;

    float getHSVColorFilter() {
    	return m_HSVColorFilter;
    }
    void setHSVColorFilter(float hsv) {
		LOGCATI("%s %d: %f --> %f",__FILE_NAME__, __LINE__,  m_HSVColorFilter, hsv);
    	m_HSVColorFilter = hsv;
    }

protected:
	volatile bool   m_IsProgramChanged;
	size_t          m_ViewportWidth;
	size_t          m_ViewportHeight;
	NativeImage     m_RenderFrame;
	TransformMatrix m_TransformMatrix;
	float 			m_HSVColorFilter;
	volatile bool   m_IsTextureDirty;
};

#endif //OPENGLCAMERA2_BYTEFLOWRENDER_H
